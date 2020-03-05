package com.abc.accounts;

import com.abc.Transaction;

import java.util.ArrayList;

public class CheckingAccount extends Account {
    //Checking accounts = rate 0.1%

    public CheckingAccount() {
        this.transactions = new ArrayList<Transaction>();
        this.setRate(0.001);
    }

    @Override
    public double interestEarned() {
        double amount = sumTransactions();

        return amount * getRate();
    }

    @Override
    public String getType() {
        return "Checking Account\n";
    }
}
