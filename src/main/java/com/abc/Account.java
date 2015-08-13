package com.abc;

import java.util.*;

public class Account {

    private final AccountType accountType;
    private double currentAmount;
    public List<Transaction> transactions;


    public Account(AccountType accountType) {
        this.accountType =  accountType;
        this.currentAmount = 0.0;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            currentAmount += amount;
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {
        if (amount > 0) {
            if(currentAmount >= amount){
                currentAmount -= amount;
                transactions.add(new Transaction(-amount));
            }
            else {
                throw new IllegalArgumentException("there are not sufficient fonds in this account");
            }
        }
        else
        {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
    }

    public double interestEarned() {

        double totalTransactions = sumTransactions();

        // In a real life application, this way of computing interest would need improving
        switch(accountType){
            case SAVINGS:
                if (totalTransactions <= 1000) {
                    return totalTransactions * 0.001;
                }
                else {
                    return 1 + (totalTransactions - 1000) * 0.002;
                }
            case MAXI_SAVINGS:
                if (withdrawalsPastTenDays()){
                    return totalTransactions * 0.001;
                }
                return totalTransactions * 0.05;
            default:
                return totalTransactions * 0.001;
        }
    }

    private boolean withdrawalsPastTenDays(){
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DATE, -10);

        for (Transaction t: transactions){
           if(t.amount < 0 && t.getTransactionDate().compareTo(cal.getTime()) > 0)
           {
               return true;
           }
        }

        return false;
    }


    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public double getCurrentAmount(){
        return currentAmount;
    }


}
