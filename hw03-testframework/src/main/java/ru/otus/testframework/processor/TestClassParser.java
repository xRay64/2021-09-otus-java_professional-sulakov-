package ru.otus.testframework.processor;

import ru.otus.testframework.annotations.After;
import ru.otus.testframework.annotations.Before;
import ru.otus.testframework.annotations.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class TestClassParser {

    private static Class testClass;
    private static Method beforeMethod;
    private static Method afterMethod;
    private static final List<Method> testMethodsList = new ArrayList<>();
    private static int completeTestCount = 0;
    private static int passTestCount = 0;
    private static int failTestCount = 0;


    public static void doTests(Class<?> clazz) {

        testClass = clazz;

        parseClass();

        //Итерируемся по тестовым методам
        for (Method testMethod : testMethodsList) {

            //Для каждого тестового метода создаем новый объект тестового класса
            Object testClassObject;
            try {
                testClassObject = testClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new RuntimeException("Тестовый клас должен иметь конструктор без аргументов");
            }

            Exception thrownException = null;
            //Вызываем before метод
            try {
                beforeMethod.invoke(testClassObject);
            } catch (Exception e) {
                thrownException = e;
            }
            //Если в методе before не получили exception - считаем что окружение подготовлено успешно и вызываем тестовый метод
            if (thrownException == null) {
                try {
                    testMethod.invoke(testClassObject);
                } catch (Exception e) {
                    thrownException = e;
                }
            }
            //В любом случае вызываем метод after
            try {
                afterMethod.invoke(testClassObject);
            } catch (Exception e) {
                thrownException = e;
            }

            //Сохраним результаты
            completeTestCount++;
            if (thrownException == null) {
                passTestCount++;
            } else {
                failTestCount++;
            }
        }
        printResults();
    }

    private static void printResults() {
        System.out.println("Выполнено тестов всего: " + completeTestCount);
        System.out.println("Выполнено тестов успешно: " + passTestCount);
        System.out.println("Выполнено тестов не успешно: " + failTestCount);
    }

    private static void parseClass() {
        for (Method method : testClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                if (beforeMethod == null) {
                    beforeMethod = method;
                } else {
                    throw new RuntimeException("Тестовый класс может содержать только один метод Before");
                }
            } else if (method.isAnnotationPresent(After.class)) {
                if (afterMethod == null) {
                    afterMethod = method;
                } else {
                    throw new RuntimeException("Тестовый класс может содержать только один метод After");
                }
            } else if (method.isAnnotationPresent(Test.class)) {
                testMethodsList.add(method);
            }
        }
    }
}
