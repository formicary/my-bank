package com.abc;

import java.util.Date;

public class Transaction {
    public final double amount;
    
    public static final int DEPOSIT = 0;
    public static final int WITHDRAW = 1;
    public static final int TRANSFER = 2;

    private Date transactionDate;
    private final int transactionType;
    private final Account recipientAccount;

    public Transaction(double amount, int type) {
        this.amount = amount;
        this.transactionType = type;
        this.transactionDate = DateProvider.getInstance().now();
        this.recipientAccount = null;
    }
    
    public Transaction(double amount, Account account) {
    	this.amount = amount;
    	this.transactionType = TRANSFER;
    	this.transactionDate = DateProvider.getInstance().now();
    	this.recipientAccount = account;
    }
    
    public int getTransactionType() {
    	return transactionType;
    }
    
    public Date getTransactionDate() {
    	return transactionDate;
    }
    
    public Account getRecipientAccount() {
    	return recipientAccount;
    }
    
    public void setTransactionDate(Date date) {
    	this.transactionDate = date;
    }

}
