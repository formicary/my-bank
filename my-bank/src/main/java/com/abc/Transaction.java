package com.abc;

import java.util.Calendar;


/**
* This class is a representation of a bank transaction.
*
* A transaction has an amount and a time.
*/
public class Transaction {

    private final double amount;
    private Calendar transactionCalendar;

    /**
     * Constructor for creating a Transaction object.
     * @param amount the amount of money involved in the transaction
     */
    public Transaction(double amount) {
        this.transactionCalendar = Calendar.getInstance();
        this.amount = amount;
    }

    /**
     * Returns the amount of money involved in the transaction.
     * @return the amount of money
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Returns the time of the transaction.
     * @return the time of the transaction
     */
    public Calendar getTransactionCalendar() {
        return transactionCalendar;
    }

    /**
     * Determines the type of transaction - withdrawal or deposit.
     * @return the type of transaction
     */
    public String transactionType() {
        return (this.getAmount() < 0 ? "withdrawal" : "deposit");
    }
}
