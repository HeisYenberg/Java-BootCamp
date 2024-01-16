package edu.school21.exeptions;

public class AlreadyAuthenticatedException extends IllegalArgumentException {
    public AlreadyAuthenticatedException(String message) {
        super(message);
    }
}
