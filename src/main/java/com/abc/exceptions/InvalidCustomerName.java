package com.abc.exceptions;

/**
 * Exception thrown by <code>Customer</code>s to indicate that 
 * provided name was not validated. 
 */

public class InvalidCustomerName extends Exception {
	
	private static final long serialVersionUID = 8524550645513049245L;

	/**
     * Constructs an <code>InvalidCustomerName</code> with the passed message.
     * @param message the detail message.
     */
     public InvalidCustomerName(final String message) {
    	  	 super(message);
     }

}
