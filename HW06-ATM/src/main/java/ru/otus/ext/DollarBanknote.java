package ru.otus.ext;

public enum DollarBanknote implements Banknote {
    ONE(1),
    TWO(2),
    FIVE(5),
    TEN(10),
    FIFTY(50),
    HUNDRED(100);

    private final int value;

    DollarBanknote(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
