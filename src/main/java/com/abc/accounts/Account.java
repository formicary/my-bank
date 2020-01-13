package com.abc.accounts;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abc.DateProvider;
import com.abc.Transaction;
import com.abc.Utilities;

public abstract class Account {

    private final AccountType accountType;
    public List<Transaction> transactions;
    public DateProvider dateProvider;
    protected double currentInterest;


    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.dateProvider = DateProvider.getInstance();

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
    
    /**
     * Calculates compound daily interest earned from the date when an account was opened.
     * @return amount of money earned depending on the account type
     */
    public abstract double interestEarned();

    @Override
    public abstract String toString();

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public AccountType getAccountType() {
        return this.accountType;
    }

    public String statementForAccount() {
        String s = "";
       //Translate to pretty account type
       s += this.toString() + "\n";

        //Now total up all the transactions
        for (Transaction t : transactions) {
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + Utilities.toDollars(t.amount) + "\n";
        }
        s += "Total " + Utilities.toDollars(sumTransactions());
        return s;
    }

    public int getTransactionPeriod(){
        int numOfTransactions = transactions.size();
        Date firstTransactionDate = transactions.get(0).getTransactionDate();
        Date lastTransactionDate = transactions.get(numOfTransactions-1).getTransactionDate();
        int numOfDays = DateProvider.getDayDifference(firstTransactionDate, lastTransactionDate);
        return numOfDays;
    }

    public double calculateDailyCompoundInterest(double interestRate, int numOfDays){
        return sumTransactions() * Math.pow(interestRate/dateProvider.getYearDays(), numOfDays);
    }

}
