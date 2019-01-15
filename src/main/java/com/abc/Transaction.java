package com.abc;


import java.util.Date;

public class Transaction {
    public final double amount;
    public final String note;

    private Date transactionDate;

    public Transaction(double amount) {
        this(amount, "None");
    }

    public Transaction(double amount, String note) {
        this.amount = amount;
        this.note = note;
        this.transactionDate = DateProvider.getInstance().now();
    }

    public Date getTransactionDate(){
        return transactionDate;
    }

}
