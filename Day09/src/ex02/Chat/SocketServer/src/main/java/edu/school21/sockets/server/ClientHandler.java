package edu.school21.sockets.server;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import edu.school21.sockets.models.Chatroom;
import edu.school21.sockets.models.Message;
import edu.school21.sockets.models.User;
import edu.school21.sockets.services.ChatroomService;
import edu.school21.sockets.services.MessagesService;
import edu.school21.sockets.services.UsersService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final UsersService usersService;
    private final MessagesService messagesService;
    private final ChatroomService chatroomService;
    private final PrintWriter out;
    private final BufferedReader in;
    private final Gson gson;

    public ClientHandler(Socket socket, UsersService usersService,
                         MessagesService messagesService,
                         ChatroomService chatroomService) throws IOException {
        this.clientSocket = socket;
        this.usersService = usersService;
        this.messagesService = messagesService;
        this.chatroomService = chatroomService;
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(
                clientSocket.getInputStream()));
        gson = new Gson();
    }

    @Override
    public void run() {
        try {
            String status = gson.fromJson(in.readLine(), String.class);
            if (status.equals("connect")) {
                connect();
            } else {
                reconnect();
            }
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void connect() throws IOException {
        out.println(gson.toJson("Hello from Server!\n" +
                "1. signIn\n" +
                "2. SignUp\n" +
                "3. Exit"));
        int command = gson.fromJson(in.readLine(), int.class);
        if (command == 1) {
            signIn();
        } else if (command == 2) {
            signUp();
        }
    }

    private void reconnect() throws IOException {
        Type mapType = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> map = gson.fromJson(in.readLine(), mapType);
        Double roomId = (Double) map.get("roomId");
        String username = (String) map.get("username");
        Optional<Chatroom> chatroom;
        Optional<User> user = usersService.getUserById(username);
        if (roomId != null) {
            chatroom = chatroomService.getRoomById(roomId.longValue());
        } else {
            chatroom = chatroomService.getLastRoom();
        }
        if (chatroom.isPresent() && user.isPresent()) {
            messaging(chatroom.get(), user.get());
        } else {
            out.println("Unable to reconnect");
        }
    }

    private void signUp() throws IOException {
        out.println(gson.toJson("Enter username:"));
        String username = gson.fromJson(in.readLine(), String.class);
        out.println(gson.toJson("Enter password:"));
        String password = gson.fromJson(in.readLine(), String.class);
        if (usersService.signUp(username, password)) {
            roomOptions(username);
        } else {
            out.println(gson.toJson("Failed to sing up!"));
        }
    }

    private void signIn() throws IOException {
        out.println(gson.toJson("Enter username:"));
        String username = gson.fromJson(in.readLine(), String.class);
        out.println(gson.toJson("Enter password:"));
        String password = gson.fromJson(in.readLine(), String.class);
        if (usersService.signIn(username, password)) {
            roomOptions(username);
        } else {
            out.println(gson.toJson("Failed to sing in!"));
        }
    }

    private void roomOptions(String username) throws IOException {
        out.println(gson.toJson("1. Create room\n" +
                "2. Choose room\n" +
                "3. Exit"));
        int command = gson.fromJson(in.readLine(), int.class);
        Optional<User> user = usersService.getUserById(username);
        if (command == 1 && user.isPresent()) {
            creatRoom(user.get());
        } else if (command == 2 && user.isPresent()) {
            chooseRoom(user.get());
        }
    }

    private void chooseRoom(User user) throws IOException {
        int i = 1;
        List<Chatroom> chatrooms = chatroomService.getRooms();
        out.println(gson.toJson("Rooms:"));
        List<String> roomsNames = new ArrayList<>(chatrooms.size());
        for (Chatroom chatroom : chatrooms) {
            roomsNames.add(i++ + ". " + chatroom.getName());
        }
        roomsNames.add(i + ". Exit");
        out.println(gson.toJson(roomsNames));
        int room = gson.fromJson(in.readLine(), int.class);
        System.out.println(room);
        if (room > 0 && room <= chatrooms.size()) {
            messaging(chatrooms.get(room - 1), user);
        }
    }

    private void creatRoom(User user) throws IOException {
        out.println(gson.toJson("Enter chatroom name:"));
        Chatroom chatroom = chatroomService.createRoom(user,
                gson.fromJson(in.readLine(), String.class));
        messaging(chatroom, user);
    }

    private void messaging(Chatroom room, User user) throws IOException {
        out.println(gson.toJson(room.getName() + " ---"));
        while (true) {
            for (Message message :
                    messagesService.getLast30FromChatroom(room.getIdentifier())) {
                out.println(gson.toJson(message.toString()));
            }
            String text = gson.fromJson(in.readLine(), String.class);
            if (text.equals("Exit")) {
                out.println(gson.toJson("You have left the chat."));
                break;
            }
            messagesService.sendMessage(room, user, text);
        }
    }
}
