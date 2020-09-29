package com.abc.exception;

public class InvalidAccountException extends IllegalArgumentException {
    public InvalidAccountException(String s) {
        super(s);
    }
}
