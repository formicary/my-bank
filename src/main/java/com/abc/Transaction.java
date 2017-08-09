package com.abc;

import java.util.Date;

public class Transaction {
    private final double amount;
    
    private final TransactionType type;

    private Date transactionDate;

    public Transaction(double amount, TransactionType type) {
        this.amount = amount;
        this.type = type;
        this.transactionDate = DateProvider.getInstance().now();
    }
    
    public Date getDate(){
    	return this.transactionDate;
    }

	public TransactionType getType() {
		return this.type;
	}

	public double getAmount(){
		return this.amount;
	}
}
