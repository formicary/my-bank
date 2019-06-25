package com.abc;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {

    public List<Transaction> transactions;

    protected int accountType = 0;
    protected double accountBalance = 0.0;

    protected String accountTypeString;

    public Account(){
        this.transactions = new ArrayList<Transaction>();
    }

    public int getAccountType(){ return accountType; }

    public String getAccountTypeString() { return accountTypeString; }

    public double getAccountBalance(){ return accountBalance; }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
            accountBalance += amount;
        }
    }

    public abstract void withdraw(double amount);

    public abstract double interestEarned();
}


/*
    public double interestEarned() {
        double amount = this.accountBalance;
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                if (amount <= 1000)
                    return amount * 0.02;
                if (amount <= 2000)
                    return 20 + (amount-1000) * 0.05;
                return 70 + (amount-2000) * 0.1;
            default:
                return amount * 0.001;
        }
    }
 */
