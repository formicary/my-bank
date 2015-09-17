package com.abc;

import java.util.Date;

public class Transaction {
	public static final int DEPOSIT = 0;
    public static final int WITHDRAWAL = 1;
    private final double amount;

    private Date transactionDate;
    private int type;		

    public Transaction(double amount, int type) {
        this.amount = amount;
        this.type = type;
        this.transactionDate = DateProvider.getInstance().now();
    }

    public double getAmount() {
    	return amount;
    }
    
    public int getType()
    {
    	return type;
    }
    
    public Date getDate() {
    	return transactionDate;
    }
}
