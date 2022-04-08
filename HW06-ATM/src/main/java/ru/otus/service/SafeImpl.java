package ru.otus.service;

import ru.otus.ext.Banknote;
import ru.otus.ext.DollarBanknote;
import ru.otus.ext.RubleBanknote;
import ru.otus.service.exception.WrongBanknoteException;
import ru.otus.service.exception.WrongMoneyException;

import java.util.HashMap;
import java.util.Map;

public class SafeImpl implements Safe {
    private final Map<Banknote, Integer> cassettes;

    public SafeImpl(String currency) {
        cassettes = new HashMap<>();
        if ("dollar".equals(currency)) {
            for (DollarBanknote banknote : DollarBanknote.values()) {
                cassettes.put(banknote, 50);
            }
        } else if ("rubel".equals(currency)) {
            for (RubleBanknote banknote : RubleBanknote.values()) {
                cassettes.put(banknote, 50);
            }
        } else {
            System.out.println("ERROR while setting cassette. Check banknotes");
        } 
    }

    @Override
    public Map<Banknote, Integer> getCassettesContent() {
        return Map.copyOf(cassettes);
    }

    @Override
    public void putBanknotes(Banknote banknote, Integer inValue) {
        if (cassettes.containsKey(banknote)) {
            int currentBanknoteValue = cassettes.get(banknote);
            if (inValue > 0) {
                cassettes.put(banknote, currentBanknoteValue + inValue);
            } else {
                throw new WrongMoneyException("inValue should be greater then zero");
            }
        } else {
            throw new WrongBanknoteException("ERROR while putting banknote: " + banknote + ". Incorrect banknote");
        }
    }

    @Override
    public void getBanknotes(Banknote banknote, Integer value) {
        if (cassettes.containsKey(banknote)) {
            int currentBanknoteValue = cassettes.get(banknote);
            if (currentBanknoteValue - value >= 0) {
                cassettes.put(banknote, currentBanknoteValue - value);
            } else {
                throw new WrongBanknoteException("WARN. Not enough banknotes");
            }
        } else {
            throw new WrongBanknoteException("ERROR while getting banknotes. Incorrect banknote");
        }
    }
}
