package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public final double amount;

    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = Calendar.getInstance().getTime();
    }
    
    public int fromCreationToDateInDays(Date d) {
    	return (int) ((d.getTime() - transactionDate.getTime()) / (1000 * 60 * 60 * 24));
    }
}
