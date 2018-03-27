package com.abc;

import java.util.Calendar;

/**
 * A class representing a transaction with a given amount and date.
 * 
 * @author Filippos Zofakis
 */
public class Transaction {
    private double amount;

    private Calendar date;

    /**
     * Constructor initialising a transaction object containing a specified
     * amount and the current date.
     * 
     * @param amount
     *            A double representing the (positive or negative) amount of the
     *            transaction.
     */
    public Transaction(double amount) {
        if (amount == 0)
            throw new IllegalArgumentException("Transaction amount cannot be zero!");

        this.amount = amount;
        this.date = Calendar.getInstance();
    }

    /**
     * Returns the amount of a transaction.
     * 
     * @return A double representing the amount of a transaction.
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Edits the amount of a transaction for correction.
     * 
     * @param newAmount
     *            The new (edited) transaction amount.
     */
    public void editAmount(double newAmount) {
        this.amount = newAmount;
    }

    /**
     * Returns the date a transaction occurred as a Calendar object. Using
     * Calendar because Date and its methods are deprecated.
     * 
     * @return An instance of Calendar representing the date a transaction took
     *         place.
     */
    public Calendar getDate() {
        return this.date;
    }
}
