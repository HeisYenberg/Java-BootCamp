package school21.spring.service.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import school21.spring.service.config.TestApplicationConfig;
import school21.spring.service.models.User;
import school21.spring.service.repositories.UsersRepository;

import java.util.Optional;

public class UsersServiceImplTest {
    private static UsersRepository repository;

    @BeforeAll
    static void init() {
        ApplicationContext context = new AnnotationConfigApplicationContext(
                TestApplicationConfig.class);
        repository = context.getBean(
                "usersRepositoryJdbcTemplate", UsersRepository.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"butterba@student.21-school.ru",
            "naboojot@student.21-school.ru", "strangem@student.21-school.ru",
            "jacquelp@student.21-school.ru", "omanytea@student.21-school.ru"})
    void testSignUp(String email) {
        UsersService service = new UsersServiceImpl(repository);
        Assertions.assertNotNull(service.signUp(email));
        Optional<User> user = repository.findByEmail(email);
        Assertions.assertTrue(user.isPresent());
        Assertions.assertEquals(email, user.get().getEmail());
    }
}
