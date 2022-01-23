package ru.otus.service;

import lombok.NoArgsConstructor;
import ru.otus.ext.Banknote;
import ru.otus.service.exception.NotEnoughBanknotesException;

import java.util.*;

@NoArgsConstructor
public class MoneyProcessorImpl implements MoneyProcessor {

    @Override
    public void putMoney(Safe safe, Map<Banknote, Integer> bunchOfMoneyToInsert) {
        for (Map.Entry<Banknote, Integer> currentBanknote : bunchOfMoneyToInsert.entrySet()) {
            safe.putBanknotes(currentBanknote.getKey(), currentBanknote.getValue());
        }
    }

    @Override
    public Map<Banknote, Integer> getMoney(Safe safe, Integer amount) throws NotEnoughBanknotesException {
        Map<Banknote, Integer> calculatedBanknotesAmount;

        calculatedBanknotesAmount = calculateBanknotesAmount(safe.getCassettesContent(), amount);

        if (!calculatedBanknotesAmount.isEmpty()) {
            calculatedBanknotesAmount.forEach(safe::getBanknotes);
        }
        return calculatedBanknotesAmount;
    }

    private Map<Banknote, Integer> calculateBanknotesAmount(Map<Banknote, Integer> mapOfContent, Integer needAmount) throws NotEnoughBanknotesException {
        Map<Banknote, Integer> calculatedBanknotes = new HashMap<>();

        Set<Banknote> keySet = mapOfContent.keySet();
        Banknote[] keyArray = keySet.toArray(new Banknote[0]);
        Arrays.sort(keyArray, (o1, o2) -> {
            int o1Value = o1.getValue();
            int o2value = o2.getValue();
            if (o1Value == o2value) {
                return 0;
            } else if (o1Value < o2value) {
                return 1;
            }
            return -1;
        });

        int rest = needAmount;

        for (Banknote currentBanknote : keyArray) {
            int amountInRest = rest / currentBanknote.getValue();
            int remainder = rest % currentBanknote.getValue();

            if (remainder < rest) {
                int banknotesInCassette = mapOfContent.get(currentBanknote);
                if (amountInRest > banknotesInCassette) {
                    rest = rest - currentBanknote.getValue() * banknotesInCassette;
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
