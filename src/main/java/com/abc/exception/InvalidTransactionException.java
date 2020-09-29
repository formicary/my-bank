package com.abc.exception;

public class InvalidTransactionException extends IllegalArgumentException {

    public InvalidTransactionException(String s) {
        super(s);
    }
}
