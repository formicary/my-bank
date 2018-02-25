package com.abc;

import java.util.Date;

/*A class to contain Transaction details: amount and date of transaction

@author Hans-Wai Chan

*/

public class Transaction {
    private final double amount;

    private Date transactionDate;

    
    /**
     * Constructor for Transaction
     * @param amount
     * 		double: transaction amount
     */
    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }
    
    /**
     * Method to get date of the transaction
     * 
     * @return date
     * 		(Object) Date: the date of the transaction
     * 
     */
    public Date getTransactionDate() {
    	return (Date) transactionDate.clone();
    }
    
    /**
     * Method to get amount of transaction
     * @return transaction amount
     */
    public double getAmount() {
    	return amount;
    }

}
