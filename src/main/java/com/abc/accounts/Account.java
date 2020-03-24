package com.abc.accounts;

import com.abc.DateProvider;
import com.abc.Transaction;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public abstract class Account {
    public static final int DAYS_OF_YEAR = 365;

    public ArrayList<Transaction> transactions;

    public abstract double calculateInterest(double amount, Date date);

    public abstract String getType();

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void deposit(double amount, Date date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount, date));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else if (getTotalAmount() <= 0) {
            throw new IllegalArgumentException("amount withdrawn can not be grater than total amount on account");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    public double sumTransactions() {
        return sumAllTransactions(true);
    }

    public double getTotalAmount() {
        return sumAllTransactions(true);
    }

    private double sumAllTransactions(boolean checkAll) {
        double totalAmount = 0.0;

        for (Transaction transaction : transactions) {
            totalAmount += transaction.amount;
        }

        return totalAmount;
    }

    public double totalInterests() {
        DateProvider dateProvider = new DateProvider();
        Date now = new Date();
        Date accountOpen = transactions.get(0).getTransactionDate();
        long totalDays = Math.abs(dateProvider.calculateDifferenceInDays(accountOpen, now, Locale.getDefault()));
        int lastHandledIndex = 0;
        double dailyAmount = 0.0;
        Calendar c = Calendar.getInstance();

        System.out.println("Days = " + totalDays);
        now = accountOpen;
        c.setTime(now);

        for (int day = 0; day < totalDays; day++) {
            Pair<Double, Integer> dailyAmountAndIndex = calculateDailyAmountAndIndex(dailyAmount, lastHandledIndex, now);
            dailyAmount = dailyAmountAndIndex.getKey();
            lastHandledIndex = dailyAmountAndIndex.getValue();


            dailyAmount *= (calculateInterest(dailyAmount, now) + 1);
            c.add(Calendar.DATE, 1);
            now = c.getTime();
        }

        dailyAmount -= getTotalAmount();
        return dailyAmount;
    }

    private Pair<Double, Integer> calculateDailyAmountAndIndex(double dailyAmount, int lastHandledIndex, Date now) {
        for (int t = lastHandledIndex; t < transactions.size(); t++) {
            if (transactions.get(t).getTransactionDate().before(now)) {
                dailyAmount += transactions.get(t).amount;
                lastHandledIndex++;
            }
        }

        return new Pair<>(dailyAmount, lastHandledIndex);
    }
}
