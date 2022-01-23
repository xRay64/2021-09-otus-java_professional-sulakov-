package ru.otus.service;

import ru.otus.ext.Banknote;
import ru.otus.service.exception.NotEnoughBanknotesException;

import java.util.Map;

public interface MoneyProcessor {
    void putMoney(Safe safe, Map<Banknote, Integer> bunchOfMoney);

    Map<Banknote, Integer> getMoney(Safe safe, Integer amount) throws NotEnoughBanknotesException;
}
