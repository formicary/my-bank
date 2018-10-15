package com.abc;

import java.util.Date;

public class Transaction {
    public final double amount;
    private final Date transactionDate;
    public final String transactionType;
            

    public Transaction(double amount, String transaction) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
        this.transactionType = transaction;
    }
    
    public Date getDate()
    {
        return transactionDate;
    }

}
