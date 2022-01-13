package ru.otus.service;

import java.util.Map;

public interface Cassette {
    Map<Integer, Integer> getMapOfContent();

    void putBanknotes(Integer banknote, Integer inValue);

    void getBanknotes(Integer banknote, Integer value);
}
