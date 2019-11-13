package com.abc;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/** Class representing a single account transaction. */
public class Transaction implements Comparable<Transaction> {
    /** The amount of money in the transaction. */
    private final double amount;

    /** The Transaction date. */
    private ZonedDateTime transactionDate;

    /**
     * Instantiates a new Transaction.
     *
     * @param amount the transaction amount
     */
    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = LocalDateTime.now().atZone(ZoneId.systemDefault());
    }

    /**
     * Specifies if the transaction was a withdrawal or deposit.
     *
     * @return the English word representation of the account type
     */
    public String getTransactionType() {
        return amount < 0 ? "Withdrawal" : "Deposit";
    }

    /**
     * Gets the transaction date.
     *
     * @return the transaction date
     */
    public ZonedDateTime getTransactionDate() {
        return transactionDate;
    }

    /**
     * Sets the transaction date.
     *
     * @param transactionDate the transaction date
     */
    public void setTransactionDate(final ZonedDateTime transactionDate) {
        this.transactionDate = transactionDate;
    }

    /**
     * Gets the transaction date in the pattern \'dd-MMM-yyyy\'.
     *
     * @return the formatted date String
     */
    public String getDateString() {
        return DateTimeFormatter.ofPattern("dd-MMM-yyyy").format(transactionDate);
    }

    /**
     * Gets the time of the transaction in the pattern \'HH:mm(z)\'.
     *
     * @return the formatted time String
     */
    public String getTimeString() {
        return DateTimeFormatter.ofPattern("HH:mm(z)").format(transactionDate);
    }

    /**
     * Gets the transaction amount.
     *
     * @return the transaction amount
     */
    public double getAmount() {
        return amount;
    }

    public int compareTo(Transaction otherTransaction) {
        return getTransactionDate().compareTo(otherTransaction.getTransactionDate());
    }

    public String toString() {
        return getTransactionType() + ": " + getAmount();
    }
}
