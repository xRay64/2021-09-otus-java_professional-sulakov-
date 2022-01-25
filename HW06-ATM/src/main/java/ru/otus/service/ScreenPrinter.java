package ru.otus.service;

import ru.otus.ext.Banknote;

import java.util.Map;

public interface ScreenPrinter {
    void printInfo(String info);

    void printError(String errorMessage);

    void printGivingMoney(Map<Banknote, Integer> givingMoney);
}
