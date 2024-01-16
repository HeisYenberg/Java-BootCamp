package school21.spring.service.models;

import java.util.Objects;
import java.util.StringJoiner;

public class User {
    private Long identifier;
    private String email;
    private String password;

    public User() {
    }

    public User(Long identifier, String email, String password) {
        this.identifier = identifier;
        this.email = email;
        this.password = password;
    }

    public Long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return identifier.equals(user.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[",
                "]")
                .add("identifier=" + identifier)
                .add("email='" + email + "'")
                .toString();
    }
}
