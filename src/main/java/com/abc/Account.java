package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

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

    protected BigDecimal interestEarned() {
        double amount = sumTransactions();
        BigDecimal exactAmount = BigDecimal.valueOf(amount);
        switch(this.accountType) {
            case SAVINGS:
                if (exactAmount <= 1000) {
                    return exactAmount.multiply(BigDecimal.valueOf(0.001);
                }
                else {
                    return BigDecimal.ONE.add((exactAmount.subtract(BigDecimal.valueOf(1000))).multiply(BigDecimal.valueOf(0.002);
                }
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                if (amount <= 1000) {
                    return exactAmount.multiply(BigDecimal.valueOf(0.02));
                }
                else if (exactAmount <= 2000) {
                    return BigDecimal.valueOf(20).add(exactAmount.subtract(BigDecimal.valueOf(1000))).multiply(BigDecimal.valueOf(0.05));
                }
                else {
                    return BigDecimal.valueOf(70).add(exactAmount.subtract(BigDecimal.valueOf(2000))).multiply(BigDecimal.valueOf(0.1));
                }
            default:
                return exactAmount.multiply(BigDecimal.valueOf(0.001));
        }
    }
}
