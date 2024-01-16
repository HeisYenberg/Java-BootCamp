package edu.school21.sockets.server;

import edu.school21.sockets.models.Message;
import edu.school21.sockets.services.MessagesService;
import edu.school21.sockets.services.UsersService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final UsersService usersService;
    private final MessagesService messagesService;
    private final PrintWriter out;
    private final BufferedReader in;

    public ClientHandler(Socket socket, UsersService usersService,
                         MessagesService messagesService) throws IOException {
        this.clientSocket = socket;
        this.usersService = usersService;
        this.messagesService = messagesService;
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(
                clientSocket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            out.println("Hello from Server!");
            String command = in.readLine();
            if ("signUp".equals(command)) {
                signUp();
            } else if ("signIn".equals(command)) {
                signIn();
            }
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private void signUp() throws IOException {
        out.println("Enter username:");
        String username = in.readLine();
        out.println("Enter password:");
        String password = in.readLine();
        if (usersService.signUp(username, password)) {
            messaging(username);
        } else {
            out.println("Failed to sing up!");
        }
    }

    private void signIn() throws IOException {
        out.println("Enter username:");
        String username = in.readLine();
        out.println("Enter password:");
        String password = in.readLine();
        if (usersService.signIn(username, password)) {
            messaging(username);
        } else {
            out.println("Failed to sing up!");
        }
    }

    private void messaging(String username) throws IOException {
        out.println("Start messaging");
        String text;
        while (!(text = in.readLine()).equals("Exit")) {
            messagesService.sendMessage(username, text);
            List<Message> messages = messagesService.getAllMessages();
            for (Message message : messages) {
                out.println(message);
            }
            out.println("Exit");
        }
        out.println("You have left the chat.");
    }
}
