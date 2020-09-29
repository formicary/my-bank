package com.abc.exception;

public class InvalidCustomerException extends IllegalArgumentException {
    public InvalidCustomerException(String s) {
        super(s);
    }
}
