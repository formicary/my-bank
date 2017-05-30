package com.abc.exceptions;

public class NonPositiveAmountException extends Exception {
    private static final long serialVersionUID = -1437017250889958835L;

    public NonPositiveAmountException(String message) {
        super(message);
    }
}
