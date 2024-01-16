package edu.school21.sockets.server;

import edu.school21.sockets.services.ChatroomService;
import edu.school21.sockets.services.MessagesService;
import edu.school21.sockets.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

@Component("server")
public class Server {
    private final UsersService usersService;
    private final MessagesService messagesService;
    private final ChatroomService chatroomService;

    @Autowired
    public Server(@Qualifier("userService") UsersService usersService,
                  @Qualifier("messagesService") MessagesService messagesService,
                  @Qualifier("chatroomService") ChatroomService chatroomService) {
        this.usersService = usersService;
        this.messagesService = messagesService;
        this.chatroomService = chatroomService;
    }

    public void start(int port) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        while (true) {
            Socket socket = serverSocket.accept();
            new Thread(new ClientHandler(socket,
                    usersService, messagesService, chatroomService)).start();
        }
    }
}
