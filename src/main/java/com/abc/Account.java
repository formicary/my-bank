package com.abc;

import java.util.ArrayList;
import java.util.List;

class Account {

    static final int CHECKING = 0;
    static final int SAVINGS = 1;
    static final int MAXI_SAVINGS = 2;

    private final int accountType;
    List<Transaction> transactions;

    Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    void depositFunds(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    void withdrawFunds(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }

    double calculateInterestEarned() {
        double amount = sumTransactions();
        switch (accountType) {
            case SAVINGS:
                if (amount <= 1000) {
                    return amount * 0.001;
                }
                else {
                    return 1 + (amount - 1000) * 0.002;
                }

            case MAXI_SAVINGS:
                if (amount <= 1000) {
                    return amount * 0.02;
                }
                else if (amount <= 2000) {
                    return 20 + (amount - 1000) * 0.05;
                }
                else {
                    return 70 + (amount - 2000) * 0.1;
                }
            default:
                return amount * 0.001;
        }
    }

    double sumTransactions() {
        return checkIfTransactionsExist();
    }

    private double checkIfTransactionsExist() {
        double amount = 0.0;
        for (Transaction t : transactions)
            amount += t.amount;
        return amount;
    }

    int getAccountType() {
        return accountType;
    }
}
