package com.abc.Transactions;

/**
 * Represents a failure during a transaction.
 */
public class TransactionException extends RuntimeException {

    /**
     * Initializes a new instance of the TransactionException class.
     *
     * @param message The message.
     */
    public TransactionException(String message) {
        super(message);
    }
}
