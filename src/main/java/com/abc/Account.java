package com.abc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Account {

    public final List<Transaction> transactions;

    public Account() {
        this.transactions = new ArrayList<>();
    }

    public double addInterestEarned() {
        return 0;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    public double sumTransactions() {
        return checkIfTransactionsExist();
    }

    private double checkIfTransactionsExist() {
        return transactions.stream().mapToDouble(transaction -> transaction.amount).sum();
    }

    public boolean isLastWithdrawInLastTenDays(List<Transaction> transactions) {
        if (transactions.isEmpty()) {
            throw new IllegalArgumentException("No transactions.");
        }
        Transaction lastTransaction = transactions.get(transactions.size() - 1);

        if (lastTransaction.amount < 0) {
            LocalDate now = LocalDate.now();
            LocalDate lastTransactionDate = lastTransaction.transactionDate;

            return lastTransactionDate.isAfter(now.minusDays(10L));
        } else {
            return false;
        }
    }

}
