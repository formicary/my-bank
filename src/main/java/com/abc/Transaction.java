package com.abc;

import java.util.Date;

import com.google.common.annotations.VisibleForTesting;

public class Transaction {
	
    public final double amount;

    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }
    
    @VisibleForTesting
    protected Transaction(double amount, Date transactionDate) {
    	 this.amount = amount;
         this.transactionDate = transactionDate;
    }

	public Date getTransactionDate() {
		return transactionDate;
	}

}
