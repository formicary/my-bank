package com.abc.bank.bankops;


import java.util.Date;

public class Transaction {


	private final double amount;
    private final Date transactionDate;
    private final Long transactionID;
    private final TransactionType transactionType;
    public Transaction(Long transactionID,Date transactionDate, double amount,TransactionType tType) {
        this.amount = amount;
        this.transactionDate = transactionDate;
        this.transactionID = transactionID;
        this.transactionType = tType;
    }
    
    public Date getTransactionDate(){
    	return transactionDate;
    }

	public Long getTransactionID() {
		return transactionID;
	}

	public double getAmount() {
		return amount;
	}


	public TransactionType getTransactionType() {
		return transactionType;
	}


}
