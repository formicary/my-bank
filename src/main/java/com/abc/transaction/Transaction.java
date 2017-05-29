package com.abc.transaction;
import com.abc.DateProvider;
import java.util.Date;

import com.abc.DateProvider;

public class Transaction {
	
    private final double amount;

    private Date transactionDate;
    // 1 for deposit and 2 for withdraw
    

	public double getAmount() {
		return amount;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	
	public Transaction(double amount) {
        this.amount = amount;
        this.setTransactionDate(DateProvider.getInstance().now());
        
    }

}
