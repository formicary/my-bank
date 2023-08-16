package com.abc;

import java.util.ArrayList;
import java.util.List;

import com.abc.DateUtils.DateChecker;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private static final double DEFAULT_INTEREST_RATE = 0.001;
    private static final double HIGHER_INTEREST_RATE_SAVINGS = 0.002;
    private static final double HIGHER_INTEREST_RATE_MAXI = 0.05;

    private final int accountType;
    public List<Transaction> transactions;
    DateChecker dateCheck = new DateChecker();

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();

    }

    /**
     * @param amount
     *               deposits money into account
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    /**
     * @param amount
     *               withdraws money from account
     */
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    /**
     * 
     * @return interest earned in checking account
     */
    public double interestEarnedChecking() {

        double amount = getAccountBalance();
        return amount * DEFAULT_INTEREST_RATE;

    }

    /**
     * 
     * @return interest earned in savings account
     */
    public double interestEarnedSavings() {
        double amount = getAccountBalance();
        if (amount <= 1000)
            return amount * DEFAULT_INTEREST_RATE;
        else
            return 1 + (amount - 1000) * HIGHER_INTEREST_RATE_SAVINGS;
    }

    /**
     * 
     * @return interest earned in maxi_savings_account
     */
    public double interestEarnedMaxiSavings() {
        double amount = getAccountBalance();
        boolean val = dateCheck.hasTransactionsWithinLastTenDays(transactions);
        if (val) {
            return amount * DEFAULT_INTEREST_RATE;
        } else {
            return amount * HIGHER_INTEREST_RATE_MAXI;
        }
    }

    /**
     * 
     * @return account balance
     */
    public double getAccountBalance() {
        return checkIfTransactionsExist(true);
    }

    /**
     * 
     * @param checkAll
     * @return total amount
     */
    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t : transactions)
            amount += t.amount;
        return amount;
    }

    /**
     * 
     * @return accountType (int): 0, 1, 2
     */
    public int getAccountType() {
        return accountType;
    }

    /**
     * 
     * @return list of transactions
     */
    public List<Transaction> getTransactionList() {
        return transactions;
    }

}
