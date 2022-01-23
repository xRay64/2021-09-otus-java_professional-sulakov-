package ru.otus.service.exception;

public class ATMException extends RuntimeException{
    public ATMException(String message) {
        super(message);
    }
}
