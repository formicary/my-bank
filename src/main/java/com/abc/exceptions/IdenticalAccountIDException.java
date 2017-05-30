package com.abc.exceptions;


public class IdenticalAccountIDException extends Exception {
    private static final long serialVersionUID = 4097682866597512010L;

    public IdenticalAccountIDException(String message) {
        super(message);
    }
}
