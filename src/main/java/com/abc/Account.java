package com.abc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<>();
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

    public double interestEarned() {
        double amount = sumTransactions();
        switch (accountType) {
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount - 1000) * 0.002;
            case MAXI_SAVINGS:
                if (amount <= 1000)
                    return amount * 0.02;
                if (amount <= 2000)
                    if (isLastWithdrawInLastTenDays(transactions)) {
                        return 20 + (amount - 1000) * 0.001;
                    } else {
                        return 20 + (amount - 1000) * 0.05;
                    }
                return 70 + (amount - 2000) * 0.1;
            default:
                return amount * 0.001;
        }
    }

    public double sumTransactions() {
        return checkIfTransactionsExist();
    }

    private double checkIfTransactionsExist() {
        return transactions.stream().mapToDouble(transaction -> transaction.amount).sum();
    }

    public int getAccountType() {
        return accountType;
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
