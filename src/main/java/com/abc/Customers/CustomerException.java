package com.abc.Customers;

/**
 * Represents a failure while processing a customer.
 */
public class CustomerException extends RuntimeException {
    /**
     * Initializes a new instance of the CustomerException class.
     *
     * @param message The message.
     */
    public CustomerException(String message) {
        super(message);
    }
}
