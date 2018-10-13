package com.abc;

import java.util.Date;

/**
 * Statement
 * This interface provides basic statement utilites
 * @author Accenture, rrana
 *
 */
public interface Statement {
	/**
	 * 
	 * @return the content of the statement
	 */
	String getContent();
	
	/**
	 * 
	 * @return the date of issue
	 */
    Date getDate();
    
 
}
