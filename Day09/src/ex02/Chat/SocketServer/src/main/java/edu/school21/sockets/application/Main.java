package edu.school21.sockets.application;

import edu.school21.sockets.config.SocketsApplicationConfig;
import edu.school21.sockets.server.Server;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String prefix = "--port=";
        if (args.length != 1 || !args[0].startsWith(prefix)) {
            System.err.println("Port argument required");
            return;
        }
        try {
            int port = Integer.parseInt(args[0].substring(prefix.length()));
            ApplicationContext context = new
                    AnnotationConfigApplicationContext(
                    SocketsApplicationConfig.class);
            Server server = context.getBean("server", Server.class);
            server.start(port);
        } catch (IOException | NumberFormatException e) {
            System.err.println(e.getMessage());
        }
    }
}
