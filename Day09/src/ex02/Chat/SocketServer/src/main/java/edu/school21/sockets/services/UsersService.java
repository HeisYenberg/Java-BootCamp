package edu.school21.sockets.services;

import edu.school21.sockets.models.User;

import java.util.Optional;

public interface UsersService {
    boolean signUp(String username, String password);

    boolean signIn(String username, String password);

    Optional<User> getUserById(String username);
}
