package ru.otus.testframework;

import ru.otus.testframework.annotations.After;
import ru.otus.testframework.annotations.Before;
import ru.otus.testframework.annotations.Test;

public class UnitTest {

    private final int i = 100;

    @Before
    public void beforeTest() {
//        Integer.parseInt("o");
        System.out.println("Inside before");
    }

    @Test
    public void shouldPassTest1() {
        System.out.println("inside test 1");
    }

    @Test
    public void shouldPassTest2() {
        System.out.println("inside test 2");
    }

    @Test
    public void shouldThrowException() {
        throw new NullPointerException();
    }

    @After
    public void afterTest() {
        System.out.println("inside after");
    }
}
