package ru.otus.testframework.processor;

import ru.otus.testframework.annotations.After;
import ru.otus.testframework.annotations.Before;
import ru.otus.testframework.annotations.Test;
import ru.otus.testframework.processor.ext.ReflectionHelper;
import ru.otus.testframework.processor.ext.TestContext;
import ru.otus.testframework.processor.ext.TestResult;

import java.lang.reflect.Method;

public class TestClassParser {

    public static void doTests(Class<?> clazz) {

        TestContext testContext = parseClass(clazz);
        TestResult testResult = new TestResult();

        //Итерируемся по тестовым методам
        for (Method testMethod : testContext.getTestMethodsList()) {

            //Для каждого тестового метода создаем новый объект тестового класса
            Object testClassObject = ReflectionHelper.instantiate(testContext.getTestClass());
            boolean isBeforeComplete = true;

            //Вызываем before метод
            if (!testContext.isBeforeMethodNull()) {
                try {
                    testContext.getBeforeMethod().invoke(testClassObject);
                } catch (Exception e) {
                    //если не смогли подготовить окружение - выполнение тестового метода бессмысленно
                    System.out.println("Exception in before method");
                    isBeforeComplete = false;
                }
            }

            if (isBeforeComplete) {
                try {
                    testMethod.invoke(testClassObject);
                } catch (Exception e) {
                    //Если ловим исключение в тестовом методе - тестирование не прерываем
                    testResult.incrementFailTest();
                    //исключение сохраняем в результатах тестирования
                    testResult.saveTestException(testMethod, e);
                } finally {
                    testResult.incrementCompleteTest();
                }
            }

            //В любом случае, не зависимо от исключений в тестовых методах, вызываем метод after
            if (!testContext.isAfterMethodNull()) {
                try {
                    testContext.getAfterMethod().invoke(testClassObject);
                } catch (Exception e) {
                    //Если ловим исключение в методе after - прерываем тестирование
                    System.out.println("Exception in after method");
                    throw new RuntimeException(e);
                }
            }
        }
        printResults(testResult);
    }

    private static void printResults(TestResult testResult) {
        System.out.println("Выполнено тестов всего: " + testResult.getCompleteTestCount());
        System.out.println("Выполнено тестов успешно: " + (testResult.getCompleteTestCount() - testResult.getFailTestCount()));
        System.out.println("Выполнено тестов не успешно: " + testResult.getFailTestCount());
        if (testResult.hasExceptions()) {
            System.out.println();
            testResult.getExceptionMap().forEach((s, e) -> {
                System.out.println("Exception in method " + s);
                e.printStackTrace();
                System.out.println();
            });
        }
    }

    private static TestContext parseClass(Class<?> clazz) {
        TestContext context = new TestContext(clazz);
        for (Method method : context.getTestClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Before.class)) {
                if (context.isBeforeMethodNull()) {
                    context.setBeforeMethod(method);
                } else {
                    throw new RuntimeException("Тестовый класс может содержать только один метод Before");
                }
            } else if (method.isAnnotationPresent(After.class)) {
                if (context.isAfterMethodNull()) {
                    context.setAfterMethod(method);
                } else {
                    throw new RuntimeException("Тестовый класс может содержать только один метод After");
                }
            } else if (method.isAnnotationPresent(Test.class)) {
                context.addTestMethod(method);
            }
        }
        return context;
    }
}
