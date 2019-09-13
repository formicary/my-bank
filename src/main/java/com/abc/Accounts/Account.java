package com.abc.Accounts;

import com.abc.Exceptions.InsufficientBalanceException;
import com.abc.Transaction;

import java.util.ArrayList;
import java.util.List;

abstract public class Account {
    private List<Transaction> transactions;

    Account() {
        this.transactions = new ArrayList<Transaction>();
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public abstract String getName();

    public void processTransaction(Transaction t) throws InsufficientBalanceException {
        double balance = calculateBalance();
        if (balance + t.getAmount() < 0) throw new InsufficientBalanceException();
        else transactions.add(t);
    }

    public abstract double interestEarned();

    public double calculateBalance() {
       return checkIfTransactionsExist(true);
    }

    double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.getAmount();
        return amount;
    }
}
