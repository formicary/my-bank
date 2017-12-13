package com.abc.banking.exceptions;

public final class AccountOpeningException extends Exception {

	private static final long serialVersionUID = -1415006748279164672L;

	public AccountOpeningException(String message, Throwable cause) {
		super(message, cause);
	}
}
