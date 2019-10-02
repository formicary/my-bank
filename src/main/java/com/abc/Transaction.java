package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
	
 	private final double amount;
    private final Date transactionDate;

    public Transaction(double amount, Date transactionDate) {
        this.amount = amount;
        this.transactionDate = transactionDate;
    }
    
    public double getAmount() {
 		return amount;
 	}

 	public Date getTransactionDate() {
 		return transactionDate;
 	}
 	
 	@Override
 	public String toString() {
 		String type = (amount > 0) ? "Deposit" : "Withdrawal";
		return String.format("  %s $%.2f", type, Math.abs(amount));
 	}

}
