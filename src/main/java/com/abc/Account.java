package com.abc;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import com.abc.DateUtils.DateChecker;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;
    DateChecker dateCheck = new DateChecker();

    // account class constructor
    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();

    }

    // add money method
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    // withdraw money method
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    // calculate checking account interest
    public double interestEarnedChecking() {
        double amount = getAccountBalance();
        return amount * 0.001;

    }

    // calculate savings account interest
    public double interestEarnedSavings() {
        double amount = getAccountBalance();
        if (amount <= 1000)
            return amount * 0.001;
        else
            return 1 + (amount - 1000) * 0.002;
    }

    // calculate maxi saver account interest
    public double interestEarnedMaxiSavings() {
        double amount = getAccountBalance();
        boolean val = dateCheck.hasTransactionsWithinLastTenDays(transactions);
        if (val) {
            return amount * 0.001;
        } else {
            return amount * 0.05;
        }
    }

    // add transactions
    public double getAccountBalance() {
        return checkIfTransactionsExist(true);
    }

    // check validity of transaction
    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t : transactions)
            amount += t.amount;
        return amount;
    }

    // get account type
    public int getAccountType() {
        return accountType;
    }

    public List<Transaction> getTransactionList() {
        return transactions;
    }

}
