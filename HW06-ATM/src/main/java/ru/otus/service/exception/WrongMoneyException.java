package ru.otus.service.exception;

public class WrongMoneyException extends RuntimeException {
    public WrongMoneyException(String message) {
        super(message);
    }
}
