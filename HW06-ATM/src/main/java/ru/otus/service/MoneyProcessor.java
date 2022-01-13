package ru.otus.service;

import ru.otus.service.exception.NotEnoughBanknotesException;

import java.util.Map;

public interface MoneyProcessor {
    void putMoney(Cassette cassette, Map<Integer, Integer> bunchOfMoney);

    Map<Integer, Integer> getMoney(Cassette cassette, Integer amount) throws NotEnoughBanknotesException;
}
