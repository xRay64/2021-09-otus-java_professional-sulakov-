package ru.otus.service;

import ru.otus.ext.DollarBanknotes;
import ru.otus.ext.RubleBanknotes;
import ru.otus.service.exception.WrongBanknoteException;
import ru.otus.service.exception.WrongMoneyException;

import java.util.HashMap;
import java.util.Map;

public class CassetteImpl implements Cassette {
    private final Map<Integer, Integer> cassetteContent;

    public CassetteImpl(String currency) {
        cassetteContent = new HashMap<>();
        if ("dollar".equals(currency)) {
            for (DollarBanknotes banknote : DollarBanknotes.values()) {
                cassetteContent.put(banknote.getValue(), 50);
            }
        } else if ("rubel".equals(currency)) {
            for (RubleBanknotes banknote : RubleBanknotes.values()) {
                cassetteContent.put(banknote.getValue(), 50);
            }
        } else {
            System.out.println("ERROR while setting cassette. Check banknotes");
        } 
    }

    @Override
    public Map<Integer, Integer> getMapOfContent() {
        return Map.copyOf(cassetteContent);
    }

    @Override
    public void putBanknotes(Integer banknote, Integer inValue) {
        if (cassetteContent.containsKey(banknote)) {
            int currentBanknoteValue = cassetteContent.get(banknote);
            if (inValue > 0) {
                cassetteContent.put(banknote, currentBanknoteValue + inValue);
            } else {
                throw new WrongMoneyException("inValue should be greater then zero");
            }
        } else {
            throw new WrongBanknoteException("ERROR while putting banknote: " + banknote + ". Incorrect banknote");
        }
    }

    @Override
    public void getBanknotes(Integer banknote, Integer value) {
        if (cassetteContent.containsKey(banknote)) {
            int currentBanknoteValue = cassetteContent.get(banknote);
            if (currentBanknoteValue - value >= 0) {
                cassetteContent.put(banknote, currentBanknoteValue - value);
            } else {
                throw new WrongBanknoteException("WARN. Not enough banknotes");
            }
        } else {
            throw new WrongBanknoteException("ERROR while getting banknotes. Incorrect banknote");
        }
    }
}
