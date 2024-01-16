package edu.school21.sockets.application;

import edu.school21.sockets.Client.Client;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String prefix = "--server-port=";
        if (args.length != 1 || !args[0].startsWith(prefix)) {
            System.err.println("Port argument required");
            return;
        }
        int port = Integer.parseInt(args[0].substring(prefix.length()));
        try {
            Client client = new Client(port);
            client.start();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
