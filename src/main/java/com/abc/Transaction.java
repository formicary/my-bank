package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public final double amount;

    public Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    public int daysCreated(Date d) {
    	return (int) ((d.getTime() - transactionDate.getTime()) / (1000 * 60 * 60 *24));
    }

}
