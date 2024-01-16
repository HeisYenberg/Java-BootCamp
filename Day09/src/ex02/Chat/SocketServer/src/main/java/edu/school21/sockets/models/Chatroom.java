package edu.school21.sockets.models;

import java.util.Objects;
import java.util.StringJoiner;

public class Chatroom {
    private Long identifier;
    private String name;
    private User owner;

    public Chatroom() {
    }

    public Chatroom(Long identifier, String name, User owner) {
        this.identifier = identifier;
        this.name = name;
        this.owner = owner;
    }

    public Long getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Long identifier) {
        this.identifier = identifier;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Chatroom chatroom = (Chatroom) o;
        return identifier.equals(chatroom.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }

    @Override
    public String toString() {
        return new StringJoiner(", ",
                Chatroom.class.getSimpleName() + "[", "]")
                .add("identifier=" + identifier)
                .add("name='" + name + "'")
                .add("owner=" + owner)
                .toString();
    }
}
