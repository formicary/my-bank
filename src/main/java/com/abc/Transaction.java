package com.abc;

import java.util.Calendar;
import java.util.Date;

/**
 * 
 * @author fearg
 *
 */
public class Transaction {

	public final double amount;
    private Date transactionDate;

    /**
     * 
     * @param amount
     */
    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    /**
     * getter for transaction date
     * @return
     */
	public Date getTransactionDate() {
		return transactionDate;
	}
    
    
}
