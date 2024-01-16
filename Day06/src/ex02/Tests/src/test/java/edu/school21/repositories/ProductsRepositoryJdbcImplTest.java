package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImplTest {
    private static final List<Product> EXPECTED_FIND_ALL_PRODUCTS =
            new LinkedList<>(Arrays.asList(
                    new Product(1L, "Apples", 150D),
                    new Product(2L, "Bananas", 80D),
                    new Product(3L, "Bread", 40D),
                    new Product(4L, "Milk", 50D),
                    new Product(5L, "Cheese", 250D)));

    private static final Product EXPECTED_FIND_BY_ID_PRODUCT =
            new Product(4L, "Milk", 50D);
    private static final Product EXPECTED_UPDATED_PRODUCT =
            new Product(4L, "Chocolate", 50D);
    private ProductsRepository repository;

    @BeforeEach
    public void init() {
        EmbeddedDatabase dataSource = new EmbeddedDatabaseBuilder().setType(
                        EmbeddedDatabaseType.HSQL).
                addScripts("/schema.sql", "/data.sql").build();
        repository = new ProductsRepositoryJdbcImpl(dataSource);
    }

    @Test
    void findAllTest() {
        Assertions.assertEquals(repository.findAll(),
                EXPECTED_FIND_ALL_PRODUCTS);
    }

    @Test
    void findByIdTest() {
        Optional<Product> product = repository.findById(4L);
        Assertions.assertTrue(product.isPresent());
        product.ifPresent(value -> Assertions.assertEquals(value,
                EXPECTED_FIND_BY_ID_PRODUCT));
    }

    @Test
    void updateTest() {
        Long updatedId = 4L;
        repository.update(new Product(updatedId, "Chocolate", 50D));
        Optional<Product> product = repository.findById(updatedId);
        Assertions.assertTrue(product.isPresent());
        product.ifPresent(value -> Assertions.assertEquals(value,
                EXPECTED_UPDATED_PRODUCT));
    }

    @Test
    void saveTest() {
        Long savedId = 6L;
        Product product = new Product(savedId, "Chocolate", 50D);
        repository.save(product);
        Optional<Product> optional = repository.findById(savedId);
        Assertions.assertTrue(optional.isPresent());
        optional.ifPresent(value -> Assertions.assertEquals(value, product));
    }

    @Test
    public void deleteTest() {
        Long deletedId = 4L;
        repository.delete(deletedId);
        Optional<Product> product = repository.findById(deletedId);
        Assertions.assertFalse(product.isPresent());
    }
}
