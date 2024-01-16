package edu.school21.sockets.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private final Socket socket;
    private final PrintWriter out;
    private final BufferedReader in;
    private final Scanner scanner;

    public Client(int port) throws IOException {
        socket = new Socket("localhost", port);
        out = new PrintWriter(socket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        scanner = new Scanner(System.in);
    }

    public void start() throws IOException {
        String greeting = in.readLine();
        if (greeting.equals("Hello from Server!")) {
            System.out.println(greeting);
            String command = scanner.nextLine();
            out.println(command);
            if (command.equals("signUp") || command.equals("signIn")) {
                enterCredentials();
                try {
                    exchangeMessages();
                } catch (NoSuchMethodException | IllegalAccessException |
                         InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            } else {
                System.err.println("Invalid command");
            }
        } else {
            System.out.println("Unrecognized greeting");
        }
        scanner.close();
        in.close();
        out.close();
        socket.close();
    }

    private void enterCredentials() throws IOException {
        System.out.println(in.readLine());
        out.println(scanner.nextLine());
        System.out.println(in.readLine());
        out.println(scanner.nextLine());
    }

    private void exchangeMessages()
            throws IOException, NoSuchMethodException,
            InvocationTargetException, IllegalAccessException {
        System.out.println(in.readLine());
        while (true) {
            String text = scanner.nextLine();
            out.println(text);
            if (text.equals("Exit")) {
                break;
            }
            String message;
            while (!(message = in.readLine()).equals("Exit")) {
                System.out.println(message);
            }
        }
        System.out.println(in.readLine());
    }
}
