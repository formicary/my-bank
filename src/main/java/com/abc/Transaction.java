package com.abc;

import java.util.Date;

public class Transaction {
	
	public final double amount;
	private Date transactionDate;

	public Transaction(double amount) {
		this.amount = amount;
		this.transactionDate = DateProvider.now();
		
		//System.out.println(transactionDate);
	}
	
	public Transaction(double amount, Date transactionDate) {
		this.amount = amount;
		this.transactionDate = transactionDate;
		
		//System.out.println(transactionDate);
	}
	
	public Date getDate() {
		return this.transactionDate;
	}

}
