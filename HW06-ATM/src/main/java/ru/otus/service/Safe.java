package ru.otus.service;

import ru.otus.ext.Banknote;

import java.util.Map;

public interface Safe {
    Map<Banknote, Integer> getCassettesContent();

    void putBanknotes(Banknote banknote, Integer inValue);

    void getBanknotes(Banknote banknote, Integer value);
}
