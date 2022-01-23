package ru.otus;

import ru.otus.ext.RubleBanknote;
import ru.otus.service.BalanceCheckerImpl;
import ru.otus.service.SafeImpl;
import ru.otus.service.MoneyProcessorImpl;
import ru.otus.service.ScreenPrinterImpl;

import java.util.List;

public class Application {
    public static void main(String[] args) {
        ATM atm = new ATMImpl(
                new SafeImpl("rubel")
                , new MoneyProcessorImpl()
                , new BalanceCheckerImpl()
                , new ScreenPrinterImpl()
        );

        atm.showATMBalance();

        atm.insertMoney(List.of(
                RubleBanknote.FIVE_THOUSAND
                , RubleBanknote.TWO_THOUSAND
                , RubleBanknote.TWO_THOUSAND
                , RubleBanknote.THOUSAND
                , RubleBanknote.FIVE_HUNDRED
                , RubleBanknote.FIVE_HUNDRED
        ));

        atm.showATMBalance();

        atm.getMoney(-5001);

        atm.getMoney(4950);
    }
}
