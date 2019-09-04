package com.abc;

import java.text.DecimalFormat;

/**
 * Account subclass with an interest rate depending on the amount the Customer has in the account
 * 
 * @author Accenture 
 * @author Liam
 *
 */
public class SavingAccount extends Account {
	
	public static final String ACCOUNT_TYPE = "Saving Account"; //String with name of account type
 
	
	public static final double FIRST_INTEREST_RATE = 0.1; //in terms of %, interest rate if less then SECOND_RATE_START_AMOUNT
	public static final double SECOND_INTEREST_RATE = 0.2; //Interest rate if greater then SECOND_RATE_START_AMOUNT
	public static final double SECOND_RATE_START_AMOUNT = 1000.0; //Value that amount attribute must be greater then to qualify for SECOND_INTEREST_RATE
	
	/**
	 * Constructor that produces a SavingAccount belonging to the passed in Customer
	 * @param owner Customer who the Account belongs to 
	 */
	public SavingAccount(Customer owner) {
		super(owner);
	}

	/**
	 * Method that provides interest daily to the account. Interest rate used depends on the amount the account has
	 */
	@Override
	public void interestEarned() {
		if (amount > 0) {
			if (amount > SECOND_RATE_START_AMOUNT) {
				//double newInterest = Math.floor((amount * InterestRateConversion(SECOND_INTEREST_RATE)) * 10000.0) / 10000.0;
				//interestEarned += newInterest;
				//amount += newInterest;
				//amount = Floor4DP(amount);
				double newAmount = Math.floor((amount * InterestRateConversion(SECOND_INTEREST_RATE)) * 10000.0) / 10000.0;
	    		interestEarned += (newAmount-amount);
	    		amount = newAmount;
			} else {
				//double newInterest = Math.floor((amount * InterestRateConversion(FIRST_INTEREST_RATE)) * 10000.0) / 10000.0;
				//interestEarned += newInterest;
				//amount += newInterest;
				//amount = Floor4DP(amount);
				double newAmount = Math.floor((amount * InterestRateConversion(FIRST_INTEREST_RATE)) * 10000.0) / 10000.0;
	    		interestEarned += (newAmount-amount);
	    		amount = newAmount;
			}
		}
	}
	
	/**
	 * Method that returns the ACCOUNT_TYPE attribute for Customer Statements, Interest Statements and debug
	 * @return String ACCOUNT_TYPE String
	 */
	@Override
	public String getAccountType() {
		return ACCOUNT_TYPE;
	}
	

}
