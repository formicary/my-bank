package com.abc;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;


class Account {

    static final int CHECKING = 0;
    static final int SAVINGS = 1;
    static final int MAXI_SAVINGS = 2;

    private final int accountType;
    List<Transaction> transactions;

    Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<>();
    }

    void depositFunds(double amount) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();

        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount, format.format(date)));
        }
    }

    void depositFunds(double amount, String customTransactionDate) throws ParseException {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount, customTransactionDate));
        }
    }

    void withdrawFunds(double amount) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount, format.format(date)));
        }
    }

    void withdrawFunds(double amount, String customTransactionDate) throws ParseException {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount, customTransactionDate));
        }
    }


    double calculateInterestEarned() {
        Date accountOpenDate = transactions.get(0).TRANSACTION_DATE;
        Date today = DateProvider.getInstance().now();
        int accountAge = daysBetweenTwoDates(accountOpenDate, today);
        double amount = sumTransactions();
        double dailyInterestRate;
        double interestRate;

        switch (accountType) {
            case SAVINGS:
                if (amount <= 1000) {
                    dailyInterestRate = 0.001 / 365.5;
                    interestRate = dailyInterestRate * accountAge;
                    return amount * interestRate;
                } else {
                    dailyInterestRate = 0.002 / 365.5;
                    interestRate = dailyInterestRate * accountAge;
                    return 1 + (amount - 1000) * interestRate;
                }

            case MAXI_SAVINGS:
                if (!withdrawnFundsInLastTenDays()) {
                    dailyInterestRate = 0.05 / 365.5;
                    interestRate = dailyInterestRate * accountAge;
                    return amount * interestRate;
                } else {
                    dailyInterestRate = 0.001 / 365.5;
                    interestRate = dailyInterestRate * accountAge;
                    return amount * interestRate;
                }

            default:
                dailyInterestRate = 0.001 / 365.5;
                interestRate = dailyInterestRate * accountAge;
                return amount * interestRate;
        }
    }


    double sumTransactions() {
        return checkIfTransactionsExist();
    }

    boolean withdrawnFundsInLastTenDays() {
        Date currentDate = DateProvider.getInstance().now();
        for (Transaction t : transactions) {
            if (t.AMOUNT < 0) {
                if (daysBetweenTwoDates(t.TRANSACTION_DATE, currentDate) <= 10) {
                    return true;
                }
            }
        }
        return false;
    }

    int daysBetweenTwoDates(Date firstDate, Date secondDate) {
        long differenceInMilliseconds = Math.abs(secondDate.getTime() - firstDate.getTime());
        long difference = TimeUnit.DAYS.convert(differenceInMilliseconds, TimeUnit.MILLISECONDS);

        return Math.toIntExact(difference);
    }

    private double checkIfTransactionsExist() {
        double amount = 0.0;
        for (Transaction t : transactions)
            amount += t.AMOUNT;
        return amount;
    }

    int getAccountType() {
        return accountType;
    }
}
