package edu.school21.services;

import edu.school21.exeptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class UsersServiceImplTest {
    private UsersRepository repository;
    private UsersServiceImpl service;
    private User user;

    @BeforeEach
    void init() {
        repository = Mockito.mock(UsersRepository.class);
        service = new UsersServiceImpl(repository);
        user = new User(1L, "butterba", "password1", false);
        Mockito.when(repository.findByLogin(user.getLogin())).thenReturn(user);
    }

    @Test
    void authenticationFalse() {
        Assertions.assertFalse(
                service.authenticate(user.getLogin(), "wrong password"));
    }

    @Test
    void authenticationTrue() {
        Mockito.doAnswer(update -> {
            user.setAuthentication(true);
            return null;
        }).when(repository).update(user);
        Mockito.doNothing().when(repository).update(user);
        Assertions.assertTrue(
                service.authenticate(user.getLogin(), user.getPassword()));
        Assertions.assertTrue(user.getAuthentication());
    }

    @Test
    void authenticationThrow() {
        user.setAuthentication(true);
        Assertions.assertThrows(AlreadyAuthenticatedException.class,
                () -> service.authenticate(user.getLogin(),
                        user.getPassword()));
    }
}
