package com.abc.exceptions;


public class CustomerNotExistException extends Exception {
    private static final long serialVersionUID = -2428240201414644506L;

    public CustomerNotExistException(String message) {
        super(message);
    }
}
