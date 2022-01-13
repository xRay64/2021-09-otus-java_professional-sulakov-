package ru.otus.service.exception;

public class NotEnoughBanknotesException extends Throwable{
    public NotEnoughBanknotesException(String message) {
        super(message);
    }
}
