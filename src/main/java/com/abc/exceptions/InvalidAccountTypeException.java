package com.abc.exceptions;

/**
 * Exception thrown by <code>Account</code>s to indicate that 
 * used AccountType is not properly handled.
 */

public class InvalidAccountTypeException extends Exception {

	private static final long serialVersionUID = 7017599837800475526L;
	
	 /**
     * Constructs an <code>InvalidAccountTypeException</code> with the passed message.
     * @param message the detail message.
     */
     public InvalidAccountTypeException(final String message) {
    	  	 super(message);
     }

}
