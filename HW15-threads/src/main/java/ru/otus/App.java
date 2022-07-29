package ru.otus;

import java.util.ArrayList;
import java.util.List;

public class App {
    private String lastThreadName = "lastThread";
    private final List<Integer> ints = fillNumbers(2);
    private int counter = 0;

    public static void main(String[] args) {
        App app = new App();
        app.go();
    }

    public void go() {
        new Thread(this::action, "firstThread").start();
        new Thread(this::action, "lastThread").start();
    }

    public List<Integer> fillNumbers(int counter) {
        List<Integer> result = new ArrayList<>();
        result.add(1);
        for (int i = 0; i < counter; i++) {
            for (int j = 2; j < 11; j++) {
                if (i % 2 == 0) {
                    result.add(j);
                } else {
                    result.add(11 - j);
                }
            }
        }

        return result;
    }

    public synchronized void action() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                String currentThreadName = Thread.currentThread().getName();
                while (lastThreadName.equals(currentThreadName)) {
                    this.wait();
                }

                if (counter > this.ints.size() - 1) {
                    lastThreadName = currentThreadName;
                    notifyAll();
                    break;
                }

                System.out.println("[" + currentThreadName + "] => " + ints.get(counter));

                if ("lastThread".equals(currentThreadName)) {
                    this.counter++;
                }

                sleep();

                lastThreadName = currentThreadName;
                notifyAll();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void sleep() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
