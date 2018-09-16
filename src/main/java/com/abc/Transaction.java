package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public final double amount;
    
    public static final int DEPOSIT = 0;
    public static final int WITHDRAW = 1;
    public static final int TRANSFER = 2;

    private Date transactionDate;
    private final int transactionType; 

    public Transaction(double amount, int type) {
        this.amount = amount;
        this.transactionType = type;
        this.transactionDate = DateProvider.getInstance().now();
    }
    
    public int getTransactionType() {
    	return transactionType;
    }
    
    public Date getTransactionDate() {
    	return transactionDate;
    }

}
