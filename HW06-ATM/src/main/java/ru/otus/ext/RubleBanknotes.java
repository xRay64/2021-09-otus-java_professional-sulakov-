package ru.otus.ext;

import java.util.List;

public class RubleBanknotes implements Banknotes {
    private final List<Integer> banknotesList = List.of(50, 100, 200, 500, 1000, 2000, 5000);

    @Override
    public List<Integer> getBanknotesList() {
        return this.banknotesList;
    }
}