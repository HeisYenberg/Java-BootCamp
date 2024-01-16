package edu.school21.sockets.Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
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
        in = new BufferedReader(
                new InputStreamReader(socket.getInputStream()));
        scanner = new Scanner(System.in);
    }

    public void start() throws IOException {
        String greeting = in.readLine();
        if (greeting.equals("Hello from Server!")) {
            singUp(greeting);
        } else {
            System.out.println("Unrecognized greeting");
        }
        scanner.close();
        in.close();
        out.close();
        socket.close();
    }

    private void singUp(String greeting) throws IOException {
        System.out.println(greeting);
        out.println(scanner.nextLine());
        String message = in.readLine();
        if (message != null) {
            System.out.println(message);
            out.println(scanner.nextLine());
            System.out.println(in.readLine());
            out.println(scanner.nextLine());
            System.out.println(in.readLine());
        } else {
            System.err.println("Invalid command");
        }
    }
}
