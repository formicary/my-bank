package com.abc.accounts;

import com.abc.Transaction;

import java.util.List;

public abstract class Account {
    public List<Transaction> transactions;
    private double rate;
    //TODO poriesit rate --> musi to byt premenna? Ako to zakomponovat tak aby to stacilo zmenit v kode raz?

    public abstract double interestEarned();
    public abstract String getType();

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

    public double sumTransactions() {
        return checkIfTransactionsExist(true);
    }

    public double getTotalAmount(){
        return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double totalAmount = 0.0;

        for (Transaction transaction : transactions){
            totalAmount += transaction.amount;
        }

        return totalAmount;
    }

    public double getRate() {
        return this.rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
}
