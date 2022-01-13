package ru.otus.service;

import java.util.Locale;
import java.util.Map;

public class ScreenPrinterImpl implements ScreenPrinter {
    @Override
    public void printInfo(String info) {
        System.out.println(info);
        System.out.println("-------------");
    }

    @Override
    public void printError(String errorMessage) {
        System.out.println(errorMessage.toUpperCase(Locale.ROOT));
        System.out.println("-------------");
    }

    @Override
    public void printGivingMoney(Map<Integer, Integer> givingMoney) {
        System.out.println("Выданы купюры");
        givingMoney.forEach((banknote, count) -> {
            System.out.printf("Номинал: %d, в количестве %d шт", banknote, count);
            System.out.println();
        });
        System.out.println("-------------");
    }
}
