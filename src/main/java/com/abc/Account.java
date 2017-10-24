package com.abc;

import java.util.ArrayList;
import java.util.Date;
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
        BigDecimal convertedAmount = BigDecimal.valueOf(amount);
        BigDecimal roundedAmount = convertedAmount.setScale(2, RoundingMode.DOWN);
        if (amount <= 0.00) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(roundedAmount.doubleValue()));
        }
    }

    protected void withdraw(double amount) {
        BigDecimal convertedAmount = BigDecimal.ZERO.subtract(BigDecimal.valueOf(amount));
        BigDecimal roundedAmount = convertedAmount.setScale(2, RoundingMode.DOWN);
        if (amount <= 0.00) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(roundedAmount.doubleValue()));
        }
    }
    
    protected int getAccountType() {
        return accountType;
    }
    
    private double checkIfTransactionsExist(boolean checkAll) {
        double total = 0.00;
        BigDecimal convertedTotal;
        BigDecimal roundedTotal;
        if (checkAll) {
            for (Transaction t: transactions) {
                total+=t.amount;
            }
        convertedTotal = BigDecimal.valueOf(total);
        roundedTotal = convertedTotal.setScale(2, RoundingMode.HALF_EVEN);
        return roundedTotal.doubleValue();
        }
    }
    
    protected double sumTransactions() {
       return checkIfTransactionsExist(true);
    }
    
    private int daysSinceLastWithdrawal() {
        Date now = DateProvider.getInstance.now();
        Date last;
        for (int i = transactions.size()-1; i>=0; i--) {
            if (transactions.get(i).amount>0) {
                last = transactions.get(i).transactionDate;
            }
        }
        return (int)((now.getTime() - last.getTime()) / (1000 * 60 * 60 * 24));
    }

    protected double interestEarned() {
        double amount = sumTransactions();
        BigDecimal convertedAmount = BigDecimal.valueOf(amount);
        BigDecimal roundedAmount = convertedAmount.setScale(2, RoundingMode.HALF_EVEN);
        switch(this.accountType) {
            case SAVINGS:
                if (amount <= 1000) {
                    return roundedAmount.multiply(BigDecimal.valueOf(0.001)).doubleValue();
                }
                else {
                    return BigDecimal.ONE.add(roundedAmount.subtract(BigDecimal.valueOf(1000))).multiply(BigDecimal.valueOf(0.002)).doubleValue();
                }
            case MAXI_SAVINGS:
                if (daysSinceLastWithdrawal() > 10) {
                    return roundedAmount.multiply(BigDecimal.valueOf(0.05)).doubleValue();
                }
                else {
                    return roundedAmount.multiply(BigDecimal.valueOf(0.001)).doubleValue();
                }
            default:
                return roundedAmount.multiply(BigDecimal.valueOf(0.001)).doubleValue();
        }
    }
}
