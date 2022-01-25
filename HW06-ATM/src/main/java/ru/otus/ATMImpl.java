package ru.otus;

import lombok.RequiredArgsConstructor;
import ru.otus.ext.Banknote;
import ru.otus.service.BalanceChecker;
import ru.otus.service.MoneyProcessor;
import ru.otus.service.Safe;
import ru.otus.service.ScreenPrinter;
import ru.otus.service.exception.ATMException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class ATMImpl implements ATM {
    private final Safe safe;
    private final MoneyProcessor moneyProcessor;
    private final BalanceChecker balanceChecker;
    private final ScreenPrinter screenPrinter;

    @Override
    public void insertMoney(List<Banknote> moneyStack) {
        Map<Banknote, Integer> bunch = new HashMap<>();
        moneyStack.forEach(i -> {
            if (bunch.containsKey(i)) {
                int current = bunch.get(i);
                bunch.put(i, ++current);
            } else {
                bunch.put(i, 1);
            }
        });
        moneyProcessor.putMoney(safe, bunch);
        screenPrinter.printInfo("Внесены купюры: " + bunch);
    }

    @Override
    public void getMoney(int amount) {
        Map<Banknote, Integer> receivedMoney;
        try {
            receivedMoney = moneyProcessor.getMoney(safe, amount);
            screenPrinter.printGivingMoney(receivedMoney);
        } catch (ATMException e) {
            screenPrinter.printError("Запрашиваемая сумма не может быть выдана");
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void showATMBalance() {
        screenPrinter.printInfo("Текущий баланс: " + balanceChecker.getATMBalance(safe));
    }
}
