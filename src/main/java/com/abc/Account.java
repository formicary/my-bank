package com.abc;

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
        this.transactions = new ArrayList<Transaction>();
    }

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

    public double interestEarned() {
        double amount = sumTransactions();

        // Interest earned is 0 if amount is 0 or a negative number
        if (amount < 1) {
            return 0;
        }

        switch (accountType) {
        case SAVINGS:
            if (amount <= 1000) {
                return amount * 0.001;
            } else {
                return 1 + (amount - 1000) * 0.002;
            }
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;

        case MAXI_SAVINGS:
            if (amount <= 1000) {
                return amount * 0.02;
            }
            if (amount <= 2000) {
                return 20 + (amount - 1000) * 0.05;
            }
            return 70 + (amount - 2000) * 0.1;

        default:
            if (amount > 1) {
                return amount * 0.001;
            } else {
                return 0;
            }
        }
    }

    public double sumTransactions() {
        return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t : transactions) {
            amount += t.amount;
        }
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

}
