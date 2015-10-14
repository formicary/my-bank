package main.java.com.abc;

import java.util.Date;

/**
 * 
 * @author Stavros Mobile
 * 
 */
public class Transaction {

	/**
	 * amount of transaction
	 */
	private final double amount;

	/**
	 * Full date of transaction
	 */
	private Date transactionDate;

	/**
	 * Day of transaction
	 */
	private int day;

	/**
	 * Constructor
	 * 
	 * @param amount
	 */
	public Transaction(double amount) {
		this.amount = amount;
		this.transactionDate = DateProvider.getInstance().now();
		this.day = DateProvider.getInstance().getDay();
	}

	/**
	 * 
	 * @return amount
	 */
	public double getAmount() {
		return this.amount;
	}

	/**
	 * 
	 * @return date
	 */
	public Date getDate() {
		return this.transactionDate;
	}

	/**
	 * 
	 * @return day - int
	 */
	public int getDay() {
		return this.day;
	}

	/**
	 * For test purpose
	 * 
	 * @param day
	 */
	// public void setDay(int day){ this.day = day; }

}
