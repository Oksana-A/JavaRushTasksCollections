package com.javarush.task.task26.task2613.command;

import com.javarush.task.task26.task2613.CashMachine;
import com.javarush.task.task26.task2613.ConsoleHelper;
import com.javarush.task.task26.task2613.CurrencyManipulator;
import com.javarush.task.task26.task2613.CurrencyManipulatorFactory;

import java.util.Collection;
import java.util.Locale;
import java.util.ResourceBundle;

class InfoCommand implements Command {
    private ResourceBundle res = ResourceBundle
            .getBundle(CashMachine.RESOURCE_PATH+"info_en", Locale.ENGLISH);

    @Override
    public void execute() {
        Collection<CurrencyManipulator> manipulators = CurrencyManipulatorFactory.getAllCurrencyManipulators();
        int sum = 0;
        for (CurrencyManipulator cm : manipulators) {
            sum += cm.getTotalAmount();
            if (cm.getTotalAmount() != 0) {
                ConsoleHelper.writeMessage(res.getString("before"));
                ConsoleHelper.writeMessage(cm + " - " + cm.getTotalAmount());
            }
        }
        if (manipulators.isEmpty() || sum == 0) {
            ConsoleHelper.writeMessage(res.getString("before"));
            ConsoleHelper.writeMessage(res.getString("no.money"));
        }
    }
}
