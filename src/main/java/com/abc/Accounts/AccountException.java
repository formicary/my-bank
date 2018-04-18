package com.abc.Accounts;

/**
 * Represents a failure while processing an account.
 */
public class AccountException extends RuntimeException {
    /**
     * Initializes a new instance of the AccountException class.
     *
     * @param message The message.
     */
    public AccountException(String message) {
        super(message);
    }
}
