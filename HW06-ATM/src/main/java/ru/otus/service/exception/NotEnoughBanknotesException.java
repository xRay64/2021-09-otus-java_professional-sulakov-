package ru.otus.service.exception;

public class NotEnoughBanknotesException extends ATMException{
    public NotEnoughBanknotesException(String message) {
        super(message);
    }
}
