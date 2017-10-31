package com.abc;

//Imports
import java.util.Date;

public class Transaction {
	//Constant for the amount being transacted
    public final double amount;

    //Variable initialised to set the transaction date
    private Date transactionDate;

    //Constructor
	public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }
	
	//Transaction date getter
	public Date getTransactionDate() {
		return transactionDate;
	}
}
