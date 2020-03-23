package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
	
    public static final int DEPOSIT = 0;
    public static final int WITHDRAW = 1;
    
    
    public final double amount;

    private Date transactionDate;
    
    public final int type;

    public Transaction(double amount, int type) {
        this.amount = amount;
        this.type = type;
        this.transactionDate = DateProvider.getInstance().now();
    }

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
    
    

}
