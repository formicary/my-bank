package com.abc;

import java.time.ZonedDateTime;
import java.util.Date;

import consts.Constants;

public class Transaction {
	
    private final double amount;
    private final Date transactionDate;
    private final String transactionType;
	
    public Transaction(double amount, String transactionType) {
    	if (transactionType.equals(Constants.WITHDRAWAL) && amount > 0) {
    		throw new IllegalArgumentException("Withdrawal Amount must be < 0");
    	} else if(transactionType.equals(Constants.DEPOSIT) && amount < 0) {
    		throw new IllegalArgumentException("Deposit Amount must be > 0");
    	}
        this.amount = amount;
        this.transactionType = transactionType;
        this.transactionDate = new Date();
    }
	
    public Date getTransactionDate() {
        return transactionDate;
    }
    public String getTransactionType() {
        return transactionType;
    }
    public double getAmount() {
        return amount;
    }
	
    public boolean isOlder10Days() {
        ZonedDateTime tenDaysAgo = ZonedDateTime.now().plusDays(-10);
        if (transactionDate.toInstant().isBefore(tenDaysAgo.toInstant())) {
            return true;
        } else {
            return false;
        }
    }

}
