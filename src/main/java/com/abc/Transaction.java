package com.abc;

import java.util.Date;


public class Transaction {
    private final double amount;
    private final Date transactionDate;
    private final TransactionType transactionType;

    /**
     * Transaction constructor
     * @param amount The amount in the transaction.
     * @param transactionType The type of transaction.
     */
    public Transaction(double amount, TransactionType transactionType) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
        this.transactionType = transactionType;
    }

    /**
     * GetTransactionDate method gets the date of the transaction.
     * @return Returns the date of the transaction.
     */
    public Date getTransactionDate() {
        return transactionDate;
    }

    /**
     * GetTransactionType method the transaction type.
     * @return Returns the transaction type.
     */
    public TransactionType getTransactionType() {
        return transactionType;
    }

    /**
     * GetAmount method gets the amount for this transaction.
     * @return Returns the amount for this transaction.
     */
    public Double getAmount() {
        return amount;
    }
}
