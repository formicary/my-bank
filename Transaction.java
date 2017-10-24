package com.abc;

import java.util.Calendar;
import java.math.BigDecimal;

public class Transaction {
    public final BigDecimal amount;
	public final boolean interest;

    private Calendar transactionDate;
    
    public Transaction(BigDecimal amount, boolean interest, Calendar date) {
        this.amount = amount;
		this.interest = interest;
        this.transactionDate = (Calendar) date.clone();
    }


	public Calendar getDate(){
		return this.transactionDate;
	}

}
