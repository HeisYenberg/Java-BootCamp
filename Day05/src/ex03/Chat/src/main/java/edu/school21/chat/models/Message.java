package edu.school21.chat.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.StringJoiner;

public class Message {
    private Long id;
    private User author;
    private Chatroom room;
    private String message;
    private LocalDateTime dateTime;

    public Message(Long id) {
        this.id = id;
    }

    public Message(Long id, User author, Chatroom room, String message,
                   LocalDateTime dateTime) {
        this.id = id;
        this.author = author;
        this.room = room;
        this.message = message;
        this.dateTime = dateTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Chatroom getRoom() {
        return room;
    }

    public void setRoom(Chatroom room) {
        this.room = room;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Message message = (Message) o;
        return id.equals(message.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return new StringJoiner(",\n", "Message : {\n",
                "\n}")
                .add("\tid=" + id)
                .add("\tauthor=" + author)
                .add("\troom=" + room)
                .add("\tmessage=\"" + message + "\"")
                .add("\tdateTime=" +
                        dateTime.format(DateTimeFormatter.ofPattern(
                                "dd/MM/yyyy HH:mm")))
                .toString();
    }
}
