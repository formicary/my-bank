package com.abc;

import java.util.Date;

/**
 * Class to handle transaction functions
 */
public class Transaction {

    public final double amount; //Amount of transaction
    public final Date transactionDate; //The date transaction is being made

    /**
     * Constructor for the Transaction class
     * @param amount the amount of transaction
     */
    public Transaction(double amount) {
        this.amount = amount;
        transactionDate = DateProvider.getInstance().now();
    }

    /**
     * Getter for the amount
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Getter of the transaction date
     * @return transaction date
     */
    public Date getTransactionDate() {
        return transactionDate;
    }
}
