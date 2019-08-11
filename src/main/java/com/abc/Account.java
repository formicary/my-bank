package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    public final int daysInTheYear = 365;
    private final int accountType;
    public List<Transaction> transactions;
    public Date currentDate = DateProvider.getInstance().now();


    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    public double dailyInterestEarned() {
        //due to the fact that the rates given are yearly rates,
        //I have divided them all by 365 so that they can compound
        //accurately as the days go on throughout the year
        double amount = sumTransactions();
        switch (accountType) {
            case SAVINGS:
                if (amount <= 1000) {
                    return amount * (0.001 / daysInTheYear);
                } else {
                    return (1000 * (0.001 / daysInTheYear)) + (amount - 1000) * (0.002 / daysInTheYear);
                }

            case MAXI_SAVINGS:

                // look at the most recent transaction
                // and see if it happened more than 10 days ag
                if (this.daysSinceLastTransaction() <= 10) {
                    return amount * (0.001 / daysInTheYear);
                } else {
                    return amount * (0.05 / daysInTheYear);
                }
            default:
                return amount * (0.001 / daysInTheYear);

        }
    }

    public int daysSinceLastTransaction() {
        Date lastTransactionDate = transactions.get(transactions.size() - 1).getTransactionDate();

        int timeElapsed = (int) ((currentDate.getTime() - lastTransactionDate.getTime()) / Transaction.millisecondsInADay);

        return timeElapsed;
    }


    public double sumTransactions() {
        return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t : transactions) {
            amount += t.amount;
        }
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }


}
