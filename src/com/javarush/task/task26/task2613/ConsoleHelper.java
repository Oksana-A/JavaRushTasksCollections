package com.javarush.task.task26.task2613;

import com.javarush.task.task26.task2613.exception.InterruptOperationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.ResourceBundle;

public class ConsoleHelper {
    private static BufferedReader bis = new BufferedReader(new InputStreamReader(System.in));
    private static ResourceBundle res = ResourceBundle
            .getBundle(CashMachine.RESOURCE_PATH+"common_en", Locale.ENGLISH);

    public static void writeMessage(String message) {
        System.out.println(message);
    }

    public static String readString() throws InterruptOperationException {
        String result = null;
        try {
            result = bis.readLine();
            if (result.toUpperCase().contains("EXIT")) {
                throw new InterruptOperationException();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public  static String askCurrencyCode() throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("choose.currency.code"));
        String str;
        while (true){
            str = ConsoleHelper.readString();
            if (str.length() != 3) {
                ConsoleHelper.writeMessage(res.getString("invalid.data"));
            }
            else
                break;
        }
        return str.toUpperCase();
    }

    public static String[] getValidTwoDigits(String currencyCode) throws InterruptOperationException {
        ConsoleHelper.writeMessage(res.getString("choose.denomination.and.count.format"));
        String[] data;
        while (true) {
            String str = ConsoleHelper.readString();
            try {
                data = str.split(" ");
                Integer.valueOf(data[0]);
                Integer.valueOf(data[1]);
            }
            catch (Exception e) {
                ConsoleHelper.writeMessage(res.getString("invalid.data"));
                continue;
            }
            break;
        }
        return data;
    }

    public static Operation askOperation() throws InterruptOperationException {
        int numberOfOperation;
        ConsoleHelper.writeMessage(res.getString("choose.operation"));
        ConsoleHelper.writeMessage("1) " + res.getString("operation.INFO"));
        ConsoleHelper.writeMessage("2) " + res.getString("operation.DEPOSIT"));
        ConsoleHelper.writeMessage("3) " + res.getString("operation.WITHDRAW"));
        ConsoleHelper.writeMessage("4) " + res.getString("operation.EXIT"));
        while (true) {
            String str = str = ConsoleHelper.readString();
            try {
                numberOfOperation = Integer.valueOf(str);
                Operation.getAllowableOperationByOrdinal(numberOfOperation);
            } catch (Exception e) {
                ConsoleHelper.writeMessage(res.getString("invalid.data"));
                continue;
            }
            break;
        }
        return Operation.getAllowableOperationByOrdinal(numberOfOperation);
    }

    public static void printExitMessage() {
        writeMessage(res.getString("the.end"));
    }
}
