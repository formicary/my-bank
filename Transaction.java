package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public final double amount;    
    private Date transactionDate;
    
    public Transaction(double amount) {
        this.amount = amount;
        this.setTransactionDate(DateProvider.getInstance().now());
    }

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

}
