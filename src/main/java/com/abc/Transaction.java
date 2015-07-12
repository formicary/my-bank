package com.abc;

import java.util.Date;

public class Transaction {
    public final double amount;

    private Date transactionDate;

    // Transaction constructor
    public Transaction(double amount) {
        this.amount = amount;
        this.setTransactionDate(DateProvider.getInstance().now());
    }
    
    // Transaction date getter
	public Date getTransactionDate() {
		return transactionDate;
	}
	
	// Transaction date setter
	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

}
