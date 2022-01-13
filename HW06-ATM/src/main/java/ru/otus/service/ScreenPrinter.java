package ru.otus.service;

import java.util.Map;

public interface ScreenPrinter {
    void printInfo(String info);

    void printError(String errorMessage);

    void printGivingMoney(Map<Integer, Integer> givingMoney);
}
