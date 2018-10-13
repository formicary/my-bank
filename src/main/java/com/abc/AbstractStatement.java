package com.abc;

import static java.lang.Math.abs;

import java.util.Date;

/**
 * 
 * @author Accenture, rrana
 * @version v2.0
 *
 */
public abstract class AbstractStatement implements Statement{
	
	private Date date;	
	
	/**
	 * Constructor
	 * @param date the issue date for this statement
	 */
	public AbstractStatement(Date date) {
		this.date = date;
	}
	
	/**
	 * @Return the issue date 
	 */
	public Date getDate() {
		return this.date;
	}
	
	/**
	 * Write in dollar format
	 * @param d the amount 
	 * @return amount in dollar format 
	 */
	protected String toDollars(double d){
		return String.format("$%,.2f", abs(d));
	}
	
}
