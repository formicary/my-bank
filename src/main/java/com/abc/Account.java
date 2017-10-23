package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;
import java.math.RoundingMode;

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
        BigDecimal d = BigDecimal.valueOf(amount);
        BigDecimal exactAmount.setScale(2, RoundingMode.HALF_EVEN);
        if (amount <= 0.00) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(exactAmount.doubleValue()));
        }
    }

    protected void withdraw(double amount) {
        BigDecimal d = BigDecimal.ZERO.subtract(BigDecimal.valueOf(amount));
        BigDecimal exactAmount.setScale(2, RoundingMode.HALF_EVEN);
        if (amount <= 0.00) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(exactAmount.doubleValue()));
        }
    }
    
    protected int getAccountType() {
        return accountType;
    }
    
    private double checkIfTransactionsExist(boolean checkAll) {
        double total = 0.00;
        BigDecimal roundedTotal;
        if (checkAll) {
            for (Transaction t: transactions) {
                total+=t.amount;
            }
        convertedTotal = BigDecimal.valueOf(total);
        BigDecimal roundedTotal = convertedTotal.setScale(2, RoundingMode.HALF_EVEN);
        return roundedTotal.doubleValue();
        }
    }
    
    protected double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    protected double interestEarned() {
        double amount = sumTransactions();
        BigDecimal convertedAmount = BigDecimal.valueof(amount);
        BigDecimal roundedAmount = convertedAmount.setScale(2, RoundingMode.HALF_EVEN);
        switch(this.accountType) {
            case SAVINGS:
                if (amount <= 1000) {
                    return roundedAmount.multiply(BigDecimal.valueOf(0.001)).doubleValue();
                }
                else {
                    return BigDecimal.ONE.add((roundedAmount.subtract(BigDecimal.valueOf(1000))).multiply(BigDecimal.valueOf(0.002).doubleValue();
                }
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                if (amount <= 1000) {
                    return roundedAmount.multiply(BigDecimal.valueOf(0.02)).doubleValue();
                }
                else if (amount <= 2000) {
                    return BigDecimal.valueOf(20).add(roundedAmount.subtract(BigDecimal.valueOf(1000))).multiply(BigDecimal.valueOf(0.05)).doubleValue();
                }
                else {
                    return BigDecimal.valueOf(70).add(roundedAmount.subtract(BigDecimal.valueOf(2000))).multiply(BigDecimal.valueOf(0.1)).doubleValue();
                }
            default:
                return roundedAmount.multiply(BigDecimal.valueOf(0.001)).doubleValue();
        }
    }
}
