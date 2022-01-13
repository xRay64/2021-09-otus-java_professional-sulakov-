package ru.otus.service;

import ru.otus.service.exception.NotEnoughBanknotesException;

import java.util.*;

public class MoneyProcessorImpl implements MoneyProcessor {

    private static MoneyProcessorImpl moneyProcessor;

    private MoneyProcessorImpl() {

    }

    public static MoneyProcessorImpl get() {
        if (moneyProcessor == null) {
            moneyProcessor = new MoneyProcessorImpl();
        }
        return moneyProcessor;
    }

    @Override
    public void putMoney(Cassette cassette, Map<Integer, Integer> bunchOfMoneyToInsert) {
        for (Map.Entry<Integer, Integer> currentBanknotes : bunchOfMoneyToInsert.entrySet()) {
            cassette.putBanknotes(currentBanknotes.getKey(), currentBanknotes.getValue());
        }
    }

    @Override
    public Map<Integer, Integer> getMoney(Cassette cassette, Integer amount) throws NotEnoughBanknotesException {
        Map<Integer, Integer> calculatedBanknotesAmount;

        calculatedBanknotesAmount = calculateBanknotesAmount(cassette.getMapOfContent(), amount);

        if (!calculatedBanknotesAmount.isEmpty()) {
            calculatedBanknotesAmount.forEach(cassette::getBanknotes);
        }
        return calculatedBanknotesAmount;
    }

    private Map<Integer, Integer> calculateBanknotesAmount(Map<Integer, Integer> mapOfContent, Integer needAmount) throws NotEnoughBanknotesException {
        Map<Integer, Integer> calculatedBanknotes = new HashMap<>();

        Set<Integer> keySet = mapOfContent.keySet();
        Integer[] keyArray = keySet.toArray(new Integer[0]);
        Arrays.sort(keyArray, Collections.reverseOrder());

        int rest = needAmount;

        for (Integer currentBanknote : keyArray) {
            int amountInRest = rest / currentBanknote;
            int remainder = rest % currentBanknote;

            if (remainder < rest) {
                int banknotesInCassette = mapOfContent.get(currentBanknote);
                if (amountInRest > banknotesInCassette) {
                    rest = rest - currentBanknote * banknotesInCassette;
                    calculatedBanknotes.put(currentBanknote, banknotesInCassette);
                } else {
                    rest = remainder;
                    calculatedBanknotes.put(currentBanknote, amountInRest);
                }
            }

        }
        if (rest != 0) {
            throw new NotEnoughBanknotesException("Not enough banknotes in cassette for requested operation");
        }

        return calculatedBanknotes;
    }
}
