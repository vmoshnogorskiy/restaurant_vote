package ru.javaops.restaurant_vote.error;

public class NotFoundException extends AppException {
    public NotFoundException(String msg) {
        super(msg);
    }
}