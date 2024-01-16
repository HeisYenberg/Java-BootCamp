package edu.school21.sockets.services;

import edu.school21.sockets.models.User;
import edu.school21.sockets.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("userService")
public class UsersServiceImpl implements UsersService {
    private final UsersRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public UsersServiceImpl(
            @Qualifier("usersRepository") UsersRepository repository,
            @Qualifier("bCryptPasswordEncoder") PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public boolean signIn(String username, String password) {
        Optional<User> user = repository.findByUsername(username);
        if (user.isPresent()) {
            String encodedPassword = user.get().getPassword();
            return encoder.matches(password, encodedPassword);
        }
        return false;
    }

    @Override
    public boolean signUp(String username, String password) {
        Long id = (long) (repository.findAll().size() + 1);
        Optional<User> optional = repository.findByUsername(username);
        if (!optional.isPresent()) {
            repository.save(new User(id, username, encoder.encode(password)));
            return true;
        }
        return false;
    }
}
