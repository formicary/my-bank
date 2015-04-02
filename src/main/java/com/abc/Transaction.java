package com.abc;

import java.util.Calendar;
import java.util.Date;

/**
 * Class Transaction represent a bank transaction
 * and has the following characteristics
 * the amount of transaction
 * and the date the transaction happened
 */
public class Transaction {

    private double amount;
    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = Calendar.getInstance().getTime();
    }

    /**
     * Overloaded Transaction Constructor for test purposes
     *
     * @param amount          amount of money for the transaction
     * @param transactionDate specific date for the transaction
     */
    public Transaction(double amount, Date transactionDate) {
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    /**
     * Retrieve amount of the transaction
     *
     * @return transaction amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Retrieve date of the transaction
     *
     * @return transaction date
     */
    public Date getTransactionDate() {
        return transactionDate;
    }

}
