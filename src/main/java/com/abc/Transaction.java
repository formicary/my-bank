package com.abc;

import java.util.Calendar;
import java.util.Date;
/**
 * Add a second constructor to create a transaction in the past for testing.
 * Change public filed to private and added a getter method for amount and date.
 * @author Andreas Neokleous
 */
public class Transaction {
    private double amount;

    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }
    
    // For testing (Maxi-Savings 10day)
    public Transaction(double amount, Date date) {
        this.amount = amount;
        this.transactionDate = date;
    }
    
    public double getAmount(){
        return this.amount;
    }
    
    public Date getTransactionDate(){
        return this.transactionDate;
    }    

}
