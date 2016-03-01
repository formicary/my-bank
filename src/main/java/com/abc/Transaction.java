package com.abc;

import java.util.Date;

/**
 * Transaction class denote each transaction of account
 * 
 * @author Fei
 *
 */
public class Transaction {
	//The transaction amount
    private final double amount;
    //The date of transaction
    private final Date transactionDate;
    //The transaction type e.g. withdraw, deposit, transfer from/to
    private final String type;
    
    /**
     * Transaction constructor
     * using current date as transaction date
     * 
     * @param amount double Amount of transaction 
     * @param type String Type of transaction
     */
    public Transaction(double amount,String type) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
        this.type = type;
    }
    
    /**
     * Transaction constructor
     * date defined by user.
     * 
     * @param amount double Amount of transaction
     * @param transactionDate Date Date of transaction
     * @param type String Transaction type
     */
    public Transaction(double amount,Date transactionDate,String type){
    	this.amount = amount;
        this.transactionDate = transactionDate;
        this.type = type;
    }
    
    
    public double getAmount(){
    	return amount;
    }
    
    public Date getDate(){
    	return transactionDate;
    }

	public String getType() {
		return type;
	}

}
