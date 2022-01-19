package ru.otus.service.exception;

public class WrongBanknoteException extends RuntimeException {
    public WrongBanknoteException(String message) {
        super(message);
    }
}
