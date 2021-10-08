package ru.otus;

import com.google.common.base.Splitter;

public class HelloOtus {
    public static void main(String[] args) {
        Splitter.on(',')
                .trimResults()
                .omitEmptyStrings()
                .split("foo,bar,,   qux")
                .forEach(System.out::println);
    }
}
