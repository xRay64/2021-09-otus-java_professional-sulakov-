package ru.otus.service;

import java.util.ArrayList;
import java.util.List;

public class SequenceGeneratorImpl implements SequenceGenerator {

    @Override
    public List<Integer> getFor(int start, int end) {
        List<Integer> resultList = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            resultList.add(i);
        }
        return resultList;
    }
}
