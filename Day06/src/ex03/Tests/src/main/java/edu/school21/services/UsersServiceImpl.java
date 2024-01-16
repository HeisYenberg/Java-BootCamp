package edu.school21.services;

import edu.school21.exeptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

public class UsersServiceImpl {
    private final UsersRepository repository;

    public UsersServiceImpl(UsersRepository repository) {
        this.repository = repository;
    }

    public boolean authenticate(String login, String password) {
        User user = repository.findByLogin(login);
        if (user.getPassword().equals(password)) {
            if (user.getAuthentication()) {
                throw new AlreadyAuthenticatedException(
                        "User already authenticated");
            }
            user.setAuthentication(true);
            repository.update(user);
            return true;
        }
        return false;
    }
}
