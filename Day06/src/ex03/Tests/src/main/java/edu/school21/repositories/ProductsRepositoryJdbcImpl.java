package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {
    private final DataSource dataSource;

    public ProductsRepositoryJdbcImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public List<Product> findAll() {
        List<Product> products = new LinkedList<>();
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM product;");
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                Long id = set.getLong("identifier");
                String name = set.getString("name");
                Double price = set.getDouble("price");
                products.add(new Product(id, name, price));
            }
        } catch (SQLException ignored) {
        }
        return products;
    }

    @Override
    public Optional<Product> findById(Long id) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM product WHERE identifier = ?;");
            statement.setLong(1, id);
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                String name = set.getString("name");
                Double price = set.getDouble("price");
                Product product = new Product(id, name, price);
                return Optional.of(product);
            }
        } catch (SQLException ignored) {
        }
        return Optional.empty();
    }

    @Override
    public void update(Product product) {
        try {
            Connection connection = dataSource.getConnection();
            StringBuilder query = new StringBuilder(50);
            Long id = product.getIdentifier();
            query.append("UPDATE product SET identifier = ").append(id);
            String name = product.getName();
            if (name != null) {
                query.append(", name = '").append(name).append('\'');
            }
            Double price = product.getPrice();
            if (price != null) {
                query.append(", price = ").append(price);
            }
            query.append(" WHERE identifier = ").append(id).append(';');
            PreparedStatement statement =
                    connection.prepareStatement(query.toString());
            if (statement.executeUpdate() == 0) {
                throw new SQLException("Failed to update product");
            }
        } catch (SQLException ignored) {
        }
    }

    @Override
    public void save(Product product) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO product (identifier, name, price) " +
                            "VALUES (?, ?, ?);");
            statement.setLong(1, product.getIdentifier());
            statement.setString(2, product.getName());
            statement.setDouble(3, product.getPrice());
            if (statement.executeUpdate() == 0) {
                throw new SQLException("Failed to save product");
            }
        } catch (SQLException ignored) {
        }
    }

    @Override
    public void delete(Long id) {
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "DELETE FROM product WHERE identifier = ?;");
            statement.setLong(1, id);
            if (statement.executeUpdate() == 0) {
                throw new SQLException("Failed to delete from product");
            }
        } catch (SQLException ignored) {
        }
    }
}
