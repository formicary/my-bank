package com.abc.accounts;

import com.abc.Transaction;

import java.util.ArrayList;
import java.util.Date;

public class CheckingAccount extends Account {
    //Checking accounts = rate 0.1%

    public CheckingAccount() {
        this.transactions = new ArrayList<Transaction>();
    }

    @Override
    public double calculateInterest(double amount, Date date) {
        return  (0.001/365);
    }

    @Override
    public String getType() {
        return "Checking Account\n";
    }
}
