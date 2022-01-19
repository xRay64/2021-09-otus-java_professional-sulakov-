package ru.otus.ext;

public enum DollarBanknotes{
    ONE(1),
    TWO(2),
    FIVE(5),
    TEN(10),
    FIFTY(50),
    HUNDRED(100);

    private final int value;

    DollarBanknotes(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
