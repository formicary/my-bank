package com.abc;

import java.util.Calendar;
import java.util.Date;

/**
 * A public class to store a transaction.
 */

public class Transaction {

    public final double amount;
    private Date transactionDate;
    private final String transactionType;

    /**
     * Constructor for the transaction
     * @param amount The amount of the transaction
     * @param transactionType e.g deposit, withdrawal, rent , bill
     */
    public Transaction(double amount, String transactionType) {
        this.amount = amount;
        this.transactionDate = Calendar.getInstance().getTime();
        this.transactionType = transactionType.toLowerCase();

    }

    /**
     * Getter for the amount
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Getter for transaction type
     * @return the transaction type
     */

    public String getTransactionType() {
        return transactionType;
    }

    /**
     * Getter for transaction date
     * @return the transaction date
     */
    public Date getTransactionDate() {
        return transactionDate;
    }

    /**
     * Setter for manually setting the transaction date. will be used for testing purposes
     * @param transactionDate
     */
    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    /**
     * toString method to show information being store for each transaction.
     * @return
     */
    public String toString() {

        String toString = transactionDate.toString() + "\n" + getAmount() + " " + getTransactionType();
        return toString;
    }

}