package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {
    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;



    private final int accountType;
    private List<Transaction> transactions;
    private double balance;

    Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        balance = 0;
    }

    public List<Transaction> getTransactions(){
        return transactions;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be greater than zero");
        } else{
            transactions.add(new Transaction(amount));
            balance += amount;
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
            balance -= amount;
        }
    }

    public double getBalance(){
        return balance;
    }

    public double interestEarned(boolean forYear) {
        final double CHECKING_INTEREST = 0.001;
        final double SAVINGS_INTEREST = 0.002;
        final double MAXI_SAVINGS_INTEREST = 0.05;
        double amount = sumTransactions();

        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return getDailyInterest(amount, CHECKING_INTEREST, forYear);
                else
                    return getDailyInterest(1000,CHECKING_INTEREST, forYear) + getDailyInterest((amount-1000),SAVINGS_INTEREST, forYear);
            case MAXI_SAVINGS:
                if (timeSinceLastWithdrawal(10))
                    return getDailyInterest(amount, MAXI_SAVINGS_INTEREST, forYear);
                return getDailyInterest(amount, CHECKING_INTEREST, forYear);
            //Default to Checking Account
            default:
                return getDailyInterest(amount, CHECKING_INTEREST, forYear);
        }
    }

    private double getDailyInterest(double amount, double annualRate, boolean forYear){
        double newAmount;
        if(!forYear) newAmount = amount*(1+(annualRate/365));
        else newAmount = amount*(Math.pow(1+(annualRate/365),365));

        return newAmount - amount;
    }

    private double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.getAmount();
        return amount;
    }

    public boolean checkIfTransactionExists(Transaction toCheck) {
        for(Transaction t : transactions){
            if(t.equals(toCheck))
                return true;
        }
        return false;
    }

    public int getAccountType() {
        return accountType;
    }

    private boolean timeSinceLastWithdrawal(int days){
        boolean success = true;
        Date daysPassed = new Date(System.currentTimeMillis() - (days * 24 * 60 * 60 * 1000));
        for(Transaction t : transactions){
            if(t.getTransDate().after(daysPassed) && (t.getAmount() < 0))
                success = false;
        }
        return success;
    }

}
