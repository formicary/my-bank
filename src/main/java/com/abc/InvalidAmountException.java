package com.abc;

/**
 * Exception class built especially for the purpose of when a customer enters an invalid deposit/withdrawal amount.
 */

public class InvalidAmountException extends Exception {

    private String errorMessage;

    /**
     * Create an InvalidAmountException object to be thrown server side if conditions are not satisfied.
     * @param errorMessage The error message chosen.
     */

    public InvalidAmountException (String errorMessage) {
        this.errorMessage = errorMessage;
    }

    /**
     * Return the appropriate error message to be used in try/catch statements.
     * @return The error message.
     */

    @Override
    public String getMessage() {

        return this.errorMessage;
    }

}
