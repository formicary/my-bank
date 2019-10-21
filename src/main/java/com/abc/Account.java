package com.abc;

import java.text.ParseException;
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

    void depositFunds(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    void depositFunds(double amount, String customTransactionDate) throws ParseException {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount, customTransactionDate));
        }
    }

    void withdrawFunds(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
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
        double amount = sumTransactions();
        switch (accountType) {
            case SAVINGS:
                if (amount <= 1000) {
                    return amount * 0.001;
                }
                else {
                    return 1 + (amount - 1000) * 0.002;
                }

            case MAXI_SAVINGS:
                if (!withdrawnFundsInLastTenDays()) {
                    return amount * 0.05;
                } else {
                    return amount * 0.001;
                }

            default:
                return amount * 0.001;
        }
    }

    double sumTransactions() {
        return checkIfTransactionsExist();
    }

    boolean withdrawnFundsInLastTenDays() {

        Date currentDate = DateProvider.getInstance().now();

        for (Transaction t : transactions) {
            if (t.AMOUNT < 0) {
                //TODO: Get last withdrawal and check if it's younger than 10 days
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
