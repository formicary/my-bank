package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    private final double amount;
    private  Date transactionDate;

    /**
     * This constructor initialises a Transaction object given an amount involved in the transaction.
     * @param amount The amount involved in the Transaction.
     */
    public Transaction(double amount) {
        this.amount = amount;

        // Removed DateProvider as the Singleton pattern should be avoided and is superfluous here.
        this.transactionDate = Calendar.getInstance().getTime();
    }

    /**
     * This method gets the date of the transaction
     * @return Transaction Date.
     */
    public Date getDate(){
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    /**
     * This method gets the date of the transaction
     * @return Transaction Date.
     */
    public double getAmount(){
        return amount;
    }
}
