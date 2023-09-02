package ru.javaops.votes.error;

public class NotFoundException extends AppException {
    public NotFoundException(String msg) {
        super(msg);
    }
}