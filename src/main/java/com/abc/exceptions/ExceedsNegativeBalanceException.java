package com.abc.exceptions;


public class ExceedsNegativeBalanceException extends Exception {
    private static final long serialVersionUID = -8538046168824688952L;

    public ExceedsNegativeBalanceException(String message) {
        super(message);
    }
}
