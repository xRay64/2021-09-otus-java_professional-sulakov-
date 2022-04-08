package ru.otus.service;

public class BalanceCheckerImpl implements BalanceChecker {
    @Override
    public String getATMBalance(Safe safe) {
        return safe.getCassettesContent().toString();
    }
}
