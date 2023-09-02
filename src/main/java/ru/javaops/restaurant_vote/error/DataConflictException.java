package ru.javaops.restaurant_vote.error;

public class DataConflictException extends AppException {
    public DataConflictException(String msg) {
        super(msg);
    }
}