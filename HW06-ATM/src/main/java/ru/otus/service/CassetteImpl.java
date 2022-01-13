package ru.otus.service;

import ru.otus.ext.Banknotes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CassetteImpl implements Cassette {
    private final Map<Integer, Integer> cassetteContent;

    public CassetteImpl(Banknotes banknotes) {
        List<Integer> banknoteList = banknotes.getBanknotesList();
        cassetteContent = new HashMap<>();
        if (!banknoteList.isEmpty()) {
            for (Integer banknote : banknotes.getBanknotesList()) {
                cassetteContent.put(banknote, 50);
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
            cassetteContent.put(banknote, currentBanknoteValue + inValue);
        } else {
            System.out.println("ERROR while putting banknotes. Incorrect banknote");
        } 
    }

    @Override
    public void getBanknotes(Integer banknote, Integer value) {
        if (cassetteContent.containsKey(banknote)) {
            int currentBanknoteValue = cassetteContent.get(banknote);
            if (currentBanknoteValue - value >= 0) {
                cassetteContent.put(banknote, currentBanknoteValue - value);
            } else {
                System.out.println("WARN. Not enough banknotes");
            }
        } else {
            System.out.println("ERROR while getting banknotes. Incorrect banknote");
        }
    }
}
