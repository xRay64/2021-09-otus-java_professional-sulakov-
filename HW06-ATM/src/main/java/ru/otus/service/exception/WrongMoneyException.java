package ru.otus.service.exception;

public class WrongMoneyException extends ATMException {
    public WrongMoneyException(String message) {
        super(message);
    }
}
