package edu.school21.repositories;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.sql.SQLException;

public class EmbeddedDataSourceTest {
    private EmbeddedDatabase dataSource;

    @BeforeEach
    public void init() {
        dataSource = new EmbeddedDatabaseBuilder().
                setType(EmbeddedDatabaseType.HSQL).
                addScripts("/schema.sql", "/data.sql").build();
    }

    @Test
    public void checkConnection() {
        try {
            Assertions.assertNotNull(dataSource.getConnection());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
