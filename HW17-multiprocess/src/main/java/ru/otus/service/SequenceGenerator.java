package ru.otus.service;

import java.util.List;

public interface SequenceGenerator {
    List<Integer> getFor(int start, int end);
}
