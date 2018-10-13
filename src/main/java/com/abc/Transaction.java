package com.abc;

import java.util.Calendar;
import java.util.Date;

/**
 * Transaction
 * @author Accenture, rrana
 * @version v2.0
 */
public interface Transaction {
    
	/**
	 * 
	 * @return the amount involved in the transaction
	 */
	double getAmount();
    
	/**
	 * 
	 * @return the type of transaction
	 */
	String getType(); 
	
	/**
	 * 
	 * @return the date when this transaction was carried out 
	 */
	Date getDate();
	
	/**
	 * Get the cash flow direction 
	 * @return true if the amount is flowing into the account
	 */
	boolean getFlowDirection();

}
