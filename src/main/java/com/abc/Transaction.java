package com.abc;

import java.util.Date;

public class Transaction {
	
	enum TransactionType {
		WITHDRAW,
		DEPOSIT,
		TRANSFER,
		RECEIVE,
	}
	
    private final double amount;
    private final TransactionType type;
    private Date date;
    private Account sender;
    private Account receiver;

    public Transaction(double amount, TransactionType transactionType) {
        this.amount = amount;
        this.type = transactionType;
        this.date = DateProvider.getInstance().now();
        this.sender = null;
        this.receiver = null;
    }
    
    public Transaction(double amount, TransactionType transactionType, Account sender, Account receiver) {
        this.amount = amount;
        this.type = transactionType;
        this.date = DateProvider.getInstance().now();
        this.sender = sender;
        this.receiver = receiver;
    }
    
    public double getAmount() {
    	return this.amount;
    }
    
    public TransactionType getType() {
    	return this.type;
    }
    
    public Date getDate() {
    	return this.date;
    }
    
    public String getSender() {
    	return (sender == null) ? "This transaction was not a transfer" : this.sender.getType().toString();
    }
    
    public String getReceiver() {
    	return (receiver == null) ? "This transaction was not a transfer" : this.receiver.getType().toString();
    }
    
    public String getStatement() {
    	String statement = "";
    	statement += this.type.toString() + ": " + this.amount;
    	switch(this.type) {
    		case TRANSFER:
    			statement += " to: " + this.getReceiver();
    			break;
    		case RECEIVE:
    			statement += " from: " + this.getSender();
    			break;
			default:
				break;
    	}
    	return statement;
    }
}
