package edu.school21.sockets.Client;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Client {
    private static final String JSON_FILE = "target/last_visit.json";
    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;
    private final Scanner scanner;
    private final Gson gson;
    private String username;
    private Long roomId;

    public Client(int port) throws IOException {
        socket = new Socket("localhost", port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        scanner = new Scanner(System.in);
        gson = new Gson();
    }

    public void start() throws IOException {
        try (FileReader reader = new FileReader(JSON_FILE)) {
            reconnect(reader);
        } catch (IOException ignored) {
            connect();
        }
        System.out.println("You have left the chat.");
        scanner.close();
        in.close();
        out.close();
        socket.close();
    }

    private void reconnect(FileReader reader) throws IOException {
        out.println(gson.toJson("reconnect"));
        Type mapType = new TypeToken<HashMap<String, Object>>() {
        }.getType();
        Map<String, Object> map = gson.fromJson(reader, mapType);
        out.println(gson.toJson(map));
        exchangeMessages();
    }

    private void connect() throws IOException {
        out.println(gson.toJson("connect"));
        System.out.println(gson.fromJson(in.readLine(), String.class));
        int command = readCommand(3);
        if (command == 1 || command == 2) {
            out.println(gson.toJson(command));
            enterCredentials();
        }
    }

    private int readCommand(int exit) {
        while (true) {
            try {
                int command = Integer.parseInt(scanner.nextLine());
                if (command > 0 || command <= exit) {
                    return command;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input, please try again");
            }
        }
    }

    private void enterCredentials() throws IOException {
        System.out.println(gson.fromJson(in.readLine(), String.class));
        username = scanner.nextLine();
        out.println(gson.toJson(username));
        System.out.println(gson.fromJson(in.readLine(), String.class));
        out.println(gson.toJson(scanner.nextLine()));
        choseRoom();
    }

    private void choseRoom() throws IOException {
        String message = gson.fromJson(in.readLine(), String.class);
        System.out.println(message);
        if (message.startsWith("Failed")) {
            return;
        }
        int command = readCommand(3);
        out.println(gson.toJson(command));
        if (command == 1) {
            System.out.println(gson.fromJson(in.readLine(), String.class));
            out.println(gson.toJson(scanner.nextLine()));
            exchangeMessages();
        } else if (command == 2) {
            System.out.println(gson.fromJson(in.readLine(), String.class));
            Type listType = new TypeToken<List<String>>() {
            }.getType();
            List<String> rooms = gson.fromJson(in.readLine(), listType);
            for (String room : rooms) {
                System.out.println(room);
            }
            roomId = (long) readCommand(rooms.size() + 1);
            out.println(gson.toJson(roomId));
            if (roomId < rooms.size()) {
                exchangeMessages();
            }
        }
    }

    private void exchangeMessages() throws IOException {
        String room = gson.fromJson(in.readLine(), String.class);
        System.out.println(room);
        if (room.equals("Unable to reconnect")) {
            return;
        }
        while (true) {
            Type listType = new TypeToken<List<String>>() {
            }.getType();
            List<String> messages = gson.fromJson(in.readLine(), listType);
            for (String message : messages) {
                System.out.println(message);
            }
            String text = scanner.nextLine();
            out.println(gson.toJson(text));
            if (text.equals("Exit")) {
                serializeToJson();
                break;
            }
        }
    }

    private void serializeToJson() {
        Map<String, Object> json = new HashMap<>();
        json.put("roomId", roomId);
        json.put("username", username);
        try (FileWriter writer = new FileWriter(JSON_FILE)) {
            gson.toJson(json, writer);
        } catch (IOException ignored) {
        }
    }
}
