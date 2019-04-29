package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;
import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.util.*;
import java.util.stream.Collectors;

class WithdrawCommand implements Command {
    CurrencyManipulator currencyManipulator;
    private ResourceBundle res = ResourceBundle
            .getBundle(CashMachine.RESOURCE_PATH+"withdraw_en", Locale.ENGLISH);

    @Override
    public void execute() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("before"));
        String currencyCode = ConsoleHelper.askCurrencyCode();
        currencyManipulator = CurrencyManipulatorFactory.getManipulatorByCurrencyCode(currencyCode);
        ConsoleHelper.writeMessage(res.getString("specify.amount"));
        int expectedAmount = 0;
        while (true) {
            String sumStr = ConsoleHelper.readString();
            try {
                expectedAmount = Integer.valueOf(sumStr);
                if (currencyManipulator.isAmountAvailable(expectedAmount)) {
                    Map<Integer, Integer> map = currencyManipulator.withdrawAmount(expectedAmount);
                    List<Integer> keyList = map.keySet().stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
                    for (Integer i : keyList) {
                        ConsoleHelper.writeMessage(i + " - " + map.get(i));
                    }
                    ConsoleHelper.writeMessage(String.format(res.getString("success.format"), expectedAmount, currencyCode));
                    break;
                }
                else {
                    ConsoleHelper.writeMessage(res.getString("not.enough.money"));
                }
            }
            catch (Exception e) {
                ConsoleHelper.writeMessage(res.getString("specify.not.empty.amount"));
            }
            ConsoleHelper.writeMessage(res.getString("exact.amount.not.available"));
        }


    }

}
