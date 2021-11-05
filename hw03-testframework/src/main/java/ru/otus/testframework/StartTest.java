package ru.otus.testframework;

import ru.otus.testframework.processor.TestClassParser;

public class StartTest {
    public static void main(String[] args) {
        TestClassParser.doTests(UnitTest.class);
    }
}
