package ru.otus.service.exception;

import ru.otus.ATM;

public class WrongBanknoteException extends ATMException {
    public WrongBanknoteException(String message) {
        super(message);
    }

}
