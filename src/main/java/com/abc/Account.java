package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {
    
    protected static final int CHECKING = 0;
    protected static final int SAVINGS = 1;
    protected static final int MAXI_SAVINGS = 2;
    protected List<Transaction> transactions;
    private final int accountType;

    protected Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    protected void deposit(double amount) {
        if (amount <= 0.00) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    protected void withdraw(double amount) {
        if (amount <= 0.00) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount));
        }
    }
    
    protected int getAccountType() {
        return accountType;
    }
    
    private double checkIfTransactionsExist(boolean checkAll) {
        double total = 0.00;
        for (Transaction t: transactions) {
            total += t.amount;
        }
        return total;
    }
    
    protected double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    protected double interestEarned() {
        double amount = sumTransactions();
        switch(this.accountType) {
            case SAVINGS:
                if (amount <= 1000) {
                    return amount * 0.001;
                }
                else {
                    return 1 + (amount-1000) * 0.002;
                }
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                if (amount <= 1000) {
                    return amount * 0.02;
                }
                else if (amount <= 2000) {
                    return 20 + (amount-1000) * 0.05;
                }
                else {
                    return 70 + (amount-2000) * 0.1;
                }
            default:
                return amount * 0.001;
        }
    }
}
