package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.NotEnoughMoneyException;

import java.util.*;

public class CurrencyManipulator {
    private String currencyCode;
    private Map<Integer, Integer> denominations = new HashMap<>();

    public CurrencyManipulator(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void addAmount(int denomination, int count) {
        if (denominations.containsKey(denomination)) {
            int newCount = denominations.get(denomination) + count;
            denominations.put(denomination, newCount);
        }
        else
            denominations.put(denomination, count);
    }

    public int getTotalAmount() {
        int sum = 0;
        for (Map.Entry<Integer, Integer> entry : denominations.entrySet()) {
            Integer key = entry.getKey();
            Integer value = entry.getValue();
            sum = sum + key*value;
        }
        return sum;
    }

    public boolean hasMoney() {
        return !denominations.isEmpty();
    }

    @Override
    public String toString() {
        return currencyCode;
    }

    public boolean isAmountAvailable(int expectedAmount) {
        return expectedAmount <= getTotalAmount();
    }

    public Map<Integer, Integer> withdrawAmount(int expectedAmount) throws NotEnoughMoneyException {
        Map<Integer, Integer> countOfDenomination = new HashMap<>();
        TreeSet<Integer> suitableDenomination = new TreeSet<>(Comparator.reverseOrder());
        for (Map.Entry<Integer, Integer> entry : denominations.entrySet()) {
            if (expectedAmount >= entry.getKey() && entry.getValue() != 0)
                suitableDenomination.add(entry.getKey());
        }
        for (Integer denomination : suitableDenomination) {
            if (expectedAmount >= denomination) {
                int count = 0;
                for (int k = 0; k < denominations.get(denomination); k++) {
                    if (expectedAmount >= denomination) {
                        expectedAmount = expectedAmount - denomination;
                        count++;
                    }
                }
                if (count != 0)
                    countOfDenomination.put(denomination, count);
            }
        }
        if (expectedAmount != 0)
            throw new NotEnoughMoneyException();
        for (Map.Entry<Integer, Integer> entry : countOfDenomination.entrySet()) {
            int newValue = denominations.get(entry.getKey()) - entry.getValue();
            if (newValue == 0)
                denominations.remove(entry.getKey());
            else
                denominations.put(entry.getKey(), newValue);
        }
        return countOfDenomination;
    }
}
