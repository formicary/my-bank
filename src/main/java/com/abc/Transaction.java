package com.abc;

import java.util.Date;

/**
 * A transaction represents a single change in the amount of money held by the account
 */
public class Transaction {
    /**
     * The transaction type allows customers and the bank to identify the reason for a transaction
     */
    public enum TransactionType {
        DEPOSIT,
        WITHDRAWAL,
        TRANSFER_IN,
        TRANSFER_OUT,
        INTEREST
    }

    /**
     * The transaction's type
     */
    private TransactionType type;

    /**
     * The amount of money carried in the transaction, will be positive if money was added to the account
     * and negative if money was taken out of the account
     */
    private final double amount;

    /**
     * The date that the transaction occurred
     */
    private Date transactionDate;

    /**
     * Constructor taking an amount and type, while automatically filling in the time
     * @param amount the amount of the transaction
     * @param type the type of transaction
     */
    public Transaction(double amount, TransactionType type) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
        this.type = type;
    }

    /**
     * Get the date of the transaction
     * @return the date of the transaction
     */
    public Date getDate() {
        return transactionDate;
    }

    /**
     * Get the amount of money in the transaction
     * @return the amount
     */
    public double getAmount() {
        return amount;
    }

    /**
     * Get the type of the transaction
     * @return the type
     */
    public TransactionType getTransactionType() {
        return type;
    }
}