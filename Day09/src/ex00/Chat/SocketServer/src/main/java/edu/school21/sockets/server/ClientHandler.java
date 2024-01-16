package edu.school21.sockets.server;

import edu.school21.sockets.services.UsersService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private final UsersService service;
    private final PrintWriter out;
    private final BufferedReader in;

    public ClientHandler(Socket socket, UsersService service)
            throws IOException {
        this.clientSocket = socket;
        this.service = service;
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
    }

    @Override
    public void run() {
        try {
            out.println("Hello from Server!");
            String command = in.readLine();
            if ("signUp".equals(command)) {
                signUp();
            }
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }

    private void signUp() throws IOException {
        out.println("Enter username:");
        String username = in.readLine();
        out.println("Enter password:");
        String password = in.readLine();
        if (service.signUp(username, password)) {
            out.println("Successful!");
        } else {
            out.println("Failed to sing up!");
        }
    }
}
