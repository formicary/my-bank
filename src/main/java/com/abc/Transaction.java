package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
	// enums?
	public static final int INTEREST = 0;
	public static final int DEPOSIT = 1;
	public static final int WITHDRAWAL = 2;
	public static final int TRANSFER_IN = 3;
	public static final int TRANSFER_OUT = 4;
	
    public final double amount;
    public final int transactionType;

    private Date transactionDate;

    public Transaction(double amount, int type) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
        this.transactionType = type;
    }

}
