package com.abc;

import java.time.LocalDate;

public class Transaction {
	
	// Transaction types for simpler printing
	public enum Type {
		WITHDRAW ("withdrawal"), DEPOSIT ("deposit"), TRANSFER ("transfer"), INTERESTS ("interests");
		private final String type;
	
	    private Type(final String type) { this.type = type; }
	    public String toString() { return type; }
		
	};
	
    private final double amount;
    private LocalDate transactionDate;
    private Type type;
    private String toFromAccountID;

    // constructor for withdrawal, deposit and interest transactions
    public Transaction(double amount, Type type) {
        this.amount = amount;
        this.type = type;
        this.transactionDate = DateProvider.getInstance().now();
    }
    
    // constructor for transfer transactions to include sender/receiver ID
    public Transaction(double amount, String account) {
        this.amount = amount;
        this.type = Type.TRANSFER;
        this.transactionDate = DateProvider.getInstance().now();
        toFromAccountID = account;
    }
    
    public double getAmount() { return amount; }
    public LocalDate getDate() { return transactionDate; }
    
    // String representation for statements
    @ Override
    public String toString() {
    	String a = String.format("$%,.2f", amount);
    	return transactionDate.toString() + ": " + type + " " + a 
    			+ (type==Type.TRANSFER? (amount<0? " (To " + toFromAccountID + ")" : " (From " + toFromAccountID + ")") : "");
    }

}
