package ru.otus;

import java.util.List;

public interface ATM {
    void insertMoney(List<Integer> moneyStack);

    void getMoney(int amount);

    void showATMBalance();
}
