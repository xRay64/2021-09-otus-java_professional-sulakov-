package ru.otus.ext;

import java.util.List;

public class DollarBanknotes implements Banknotes{
    private final List<Integer> banknotesList = List.of(1, 2, 5, 10, 20, 50, 100);

    @Override
    public List<Integer> getBanknotesList() {
        return this.banknotesList;
    }
}
