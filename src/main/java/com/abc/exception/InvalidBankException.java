package com.abc.exception;

public class InvalidBankException extends IllegalArgumentException{
    public InvalidBankException(String s) {
        super(s);
    }
}
