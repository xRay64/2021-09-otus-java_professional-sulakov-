package ru.otus;

import ru.otus.ext.Banknote;

import java.util.List;

public interface ATM {
    void insertMoney(List<Banknote> moneyStack);

    void getMoney(int amount);

    void showATMBalance();
}
