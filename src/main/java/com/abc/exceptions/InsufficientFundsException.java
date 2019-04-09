package com.abc.exceptions;

/**
 * Thrown to indicate that the account doesn't have enough funds to fulfill the transfer request.
 */
public class InsufficientFundsException extends Exception {

    /**
     * Constructs an <code>InsufficientFundsException</code> with no
     * detail message.
     */
    public InsufficientFundsException() {
    }

}
