package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    // Transaction details
    private final double amount;
    private final String transactionType;
    private Date transactionDate;
    private long transactionId;
    // Calendar for transaction class
    private static final Calendar calendar = Calendar.getInstance();

    /**
     * Constructor for transaction, requires amount for transaction and ID which is
     * unique only with respect to the other transactions on the account
     * @param amount
     * @param transactionId
     */
    public Transaction(double amount, long transactionId) {
        this.amount = amount;
        this.transactionDate = calendar.getTime();
        this.transactionId = transactionId;
        this.transactionType = amount < 0 ? "Withdrawal" : "Deposit";
    }
    /**
     * Constructor for transaction in case the transaction is declared outside of
     * the deposit and withdraw methods in the Account class
     * @param amount
     */
    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = calendar.getTime();
        this.transactionType = amount < 0 ? "Withdrawal" : "Deposit";
    }

    // Getters
    /**
     * Get transaction amount as a double
     * @return [double]
     */
    public double getAmount() {
        return amount;
    }
    /**
     * Get the date when this transaction was made
     * @return [Date]
     */
    public Date getTransactionDate() {
        return transactionDate;
    }
    /**
     * Get the type of transaction
     * @return [String]
     */
    public String getTransactionType() {
        return transactionType;
    }
    /**
     * Get the transaction id
     * @return
     */
    public long getTransactionId() {
        return transactionId;
    }
}
