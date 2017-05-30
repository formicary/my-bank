package com.abc.exceptions;


public class AccountNotExistException extends Exception {
    private static final long serialVersionUID = 2389081832381681502L;

    public AccountNotExistException(String message) {
        super(message);
    }

}
