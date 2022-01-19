package ru.otus.ext;

public enum RubleBanknotes {
    FIFTY(50),
    HUNDRED(100),
    TWO_HUNDRED(200),
    FIVE_HUNDRED(500),
    THOUSAND(1000),
    TWO_THOUSAND(2000),
    FIVE_THOUSAND(5000);

    private final int value;

    RubleBanknotes(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}