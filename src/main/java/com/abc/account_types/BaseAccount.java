package com.abc.account_types;

import com.abc.Constants;
import com.abc.Transaction;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseAccount {
    public List<Transaction> transactions;

    public BaseAccount(){
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException(Constants.GreaterThanZeroErrorMessage);
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException(Constants.GreaterThanZeroErrorMessage);
        } else {
            // Don't like the - here, could be missing
            transactions.add(new Transaction(-amount));
        }
    }

    // Protected maybe?
    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    abstract public String getAccountType();
    abstract public double getInterestEarned();
}
