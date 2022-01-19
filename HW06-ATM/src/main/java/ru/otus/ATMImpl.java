package ru.otus;

import lombok.RequiredArgsConstructor;
import ru.otus.service.BalanceChecker;
import ru.otus.service.Cassette;
import ru.otus.service.MoneyProcessor;
import ru.otus.service.ScreenPrinter;
import ru.otus.service.exception.NotEnoughBanknotesException;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@RequiredArgsConstructor
public class ATMImpl implements ATM {
    private final Cassette cassette;
    private final MoneyProcessor moneyProcessor;
    private final BalanceChecker balanceChecker;
    private final ScreenPrinter screenPrinter;

    @Override
    public void insertMoney(List<Integer> moneyStack) {
        Map<Integer, Integer> bunch = new TreeMap<>();
        moneyStack.forEach(i -> {
            if (bunch.containsKey(i)) {
                int current = bunch.get(i);
                bunch.put(i, ++current);
            } else {
                bunch.put(i, 1);
            }
        });
        moneyProcessor.putMoney(cassette, bunch);
        screenPrinter.printInfo("Внесены купюры: " + bunch);
    }

    @Override
    public void getMoney(int amount) {
        Map<Integer, Integer> receivedMoney;
        try {
            receivedMoney = moneyProcessor.getMoney(cassette, amount);
            screenPrinter.printGivingMoney(receivedMoney);
        } catch (NotEnoughBanknotesException e) {
            screenPrinter.printError("Запрашиваемая сумма не может быть выдана");
        }
    }

    @Override
    public void showATMBalance() {
        screenPrinter.printInfo("Текущий баланс: " + balanceChecker.getATMBalance(cassette));
    }
}
