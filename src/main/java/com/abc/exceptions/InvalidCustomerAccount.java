package com.abc.exceptions;

/**
 * Exception thrown by <code>Customer</code>s to indicate 
 * that there is a mismatch between given <code>Account</code>
 * and <code>Customer</code> objects.
 */

public class InvalidCustomerAccount extends Exception {

	private static final long serialVersionUID = -8563310791503647896L;
	
    /**
    * Constructs an <code>InvalidCustomerAccount</code> with the passed message.
    * @param message the detail message.
    */
	
	public InvalidCustomerAccount (final String message) {
		super(message);
	}

}
