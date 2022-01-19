package ru.otus;

import ru.otus.service.BalanceCheckerImpl;
import ru.otus.service.CassetteImpl;
import ru.otus.service.MoneyProcessorImpl;
import ru.otus.service.ScreenPrinterImpl;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        ATM atm = new ATMImpl(
                new CassetteImpl("rubel")
                , new MoneyProcessorImpl()
                , new BalanceCheckerImpl()
                , new ScreenPrinterImpl()
        );

        atm.showATMBalance();

        atm.insertMoney(List.of(5000, 2000, 2000, 1000, 500, 500));

        atm.showATMBalance();

        atm.getMoney(5001);

        atm.getMoney(4950);
    }
}
