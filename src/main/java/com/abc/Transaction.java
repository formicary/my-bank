package com.abc;

import java.util.Date;

public class Transaction {
    public final double amount;

    private Date transactionDate;

    public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}
	public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }
	
	//Method written for testing purpose only. Should not allow Back date validation.
	public Transaction(double amount, Date transactionDate) {
        this.amount = amount;
        this.transactionDate = transactionDate;
    }
	
    public Date getDate () {
    	  return transactionDate;
    	 }
    public int fromBegining(Date d) {
    	    return (int) ((d.getTime() - transactionDate.getTime()) / (1000 * 60 * 60 * 24));
    	   }

}
