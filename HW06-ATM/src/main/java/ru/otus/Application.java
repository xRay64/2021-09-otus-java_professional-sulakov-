package ru.otus;

import ru.otus.ext.RubleBanknotes;
import ru.otus.service.*;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        ATM atm = new ATM(
                new CassetteImpl(new RubleBanknotes())
                , MoneyProcessorImpl.get()
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
