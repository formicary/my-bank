package com.abc.exceptions;

/**
 * Exception thrown by <code>Account</code>s to indicate that current
 * withdrawal has failed due to exceeded funds.
 */

public class ExceededFundsException extends Exception {
		
	private static final long serialVersionUID = 4679554749223314396L;
	
     /**
     * Constructs an <code>ExceededFundsException</code> with the passed message.
     * @param message the detail message.
     */
     public ExceededFundsException(final String message) {
    	 super(message);
     }

}


