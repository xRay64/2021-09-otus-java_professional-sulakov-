package ru.otus.service.exception;

public class NotEnoughBanknotesException extends RuntimeException{
    public NotEnoughBanknotesException(String message) {
        super(message);
    }
}
