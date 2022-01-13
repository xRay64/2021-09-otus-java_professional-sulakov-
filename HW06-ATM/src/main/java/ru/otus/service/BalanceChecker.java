package ru.otus.service;

public interface BalanceChecker {
    String getATMBalance(Cassette cassette);
}
