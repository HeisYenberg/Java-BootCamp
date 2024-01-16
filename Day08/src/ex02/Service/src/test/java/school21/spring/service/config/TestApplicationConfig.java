package school21.spring.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import school21.spring.service.repositories.UsersRepository;
import school21.spring.service.repositories.UsersRepositoryJdbcTemplateImpl;

import javax.sql.DataSource;

@Configuration
@ComponentScan("school21.spring.service")
public class TestApplicationConfig {
    @Bean
    public DataSource embeddedDatabase() {
        return new EmbeddedDatabaseBuilder().
                setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql").build();
    }

    @Bean
    public UsersRepository usersRepositoryJdbcTemplate() {
        return new UsersRepositoryJdbcTemplateImpl(embeddedDatabase());
    }
}
