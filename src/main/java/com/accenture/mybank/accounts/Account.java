package com.accenture.mybank.accounts;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.accenture.mybank.Transaction;
import com.accenture.mybank.utils.DateProvider;

public abstract class Account {
    public ArrayList<Transaction> transactions;

    public abstract double calculateInterest(double amount, Date date);

    public abstract String getType();

    /**
     * This method performs deposit action for an account
     * @param amount
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    /**
     * This method performs deposit action of an account on particular date
     * @param amount
     * @param date
     */
    public void deposit(double amount, Date date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount, date));
        }
    }

    /**
     * This method performs withdrawal of money from an account
     * @param amount
     */
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    /**
     * This method gets sum of all transactions of an account
     * @return sum
     */
    public double sumTransactions() {
        return checkIfTransactionsExist(true);
    }

    /**
     * This method gets total amount of an account
     * @return
     */
    public double getTotalAmount() {
        return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double totalAmount = 0.0;

        for (Transaction transaction : transactions) {
            totalAmount += transaction.amount;
        }

        return totalAmount;
    }

    /**
     * This method calculates total interest earned by particular account from date of opening to till date
     * @return total interest earned
     */
    public double totalInterests() {
        DateProvider dateProvider = new DateProvider();
        Date now = new Date();
        Date accountOpen = transactions.get(0).getTransactionDate();
        long totalDays = Math.abs(dateProvider.calculateDifferenceInDays(accountOpen, now, Locale.getDefault()));
        int lastHandledIndex = 0;
        double dailyAmount = 0.0;
        Calendar c = Calendar.getInstance();
        now = accountOpen;
        c.setTime(now);

        for (int day = 0; day < totalDays; day++) {
            for (int t = lastHandledIndex; t < transactions.size(); t++) {
                if (transactions.get(t).getTransactionDate().before(now)) {
                    dailyAmount += transactions.get(t).amount;
                    lastHandledIndex++;
                }
            }
            dailyAmount *= (calculateInterest(dailyAmount, now) + 1);
            c.add(Calendar.DATE, 1);
            now = c.getTime();
        }

        dailyAmount -= getTotalAmount();
        return dailyAmount;
    }
}