package com.abc.banking.exceptions;

public final class TransactionException extends Exception {

	private static final long serialVersionUID = 5063236509996616425L;

	public TransactionException(String message) {
		super(message);
	}

	public TransactionException(String message, Throwable cause) {
		super(message, cause);
	}
}
