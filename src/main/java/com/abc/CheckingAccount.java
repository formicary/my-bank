package com.abc;

/**
 * Account subclass with a flat interest rate of 0.1% per annum
 * 
 * @author Accenture 
 * @author Liam
 *
 */
public class CheckingAccount extends Account {
	
	public static final String ACCOUNT_TYPE = "Checking Account"; //String with name of account type
	
	public static final double INTEREST_RATE = 0.1; //in terms of %

	/**
	 * Constructor that produces a CheckingAccount belonging to the passed in Customer
	 * @param owner Customer who the Account belongs to 
	 */
	public CheckingAccount(Customer owner) {
		super(owner);
	}
	
	/**
	 * Method that compounds daily interest onto the accounts amount value and records the earned interest
	 */
	//Method that adds interest the account has earned, depending on current account amount and account type
    public void interestEarned() {
    	if (amount > 0) {
    		double newAmount = Math.floor((amount * InterestRateConversion(INTEREST_RATE)) * 10000.0) / 10000.0;
    		interestEarned += (newAmount-amount);
    		amount = newAmount;
    		//double newInterest = Math.floor((amount * InterestRateConversion(INTEREST_RATE)) * 10000.0) / 10000.0;
        	//interestEarned += newInterest;
        	//amount += newInterest;
        	//amount = Floor4DP(amount);
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
