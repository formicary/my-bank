package com.abc;

/**
 * Account subclass with an interest rate depending on if the Customer has
 * withdrawn recently
 * 
 * @author Accenture 
 * @author Liam
 *
 */
public class MaxiSavingAccount extends Account {
	
	public static final String ACCOUNT_TYPE = "Maxi-Saving Account"; //String with name of account type

	/*
	 * Interest rates and starting values ($) before additional feature changes
	 */
	/*
	public static final double FIRST_INTEREST_RATE = 2.0; //in terms of %
	
	public static final double SECOND_INTEREST_RATE = 5.0;
	public static final double SECOND_RATE_START_AMOUNT = 1000.0;
	
	public static final double THIRD_INTEREST_RATE = 10.0;
	public static final double THIRD_RATE_START_AMOUNT = 1000.0 + SECOND_RATE_START_AMOUNT;
	*/
	
	public static final double FIRST_INTEREST_RATE = 0.1; //in terms of %, interest rate if there was a recent withdrawl
	public static final double SECOND_INTEREST_RATE = 5.0; //interest rate if there hasn't been a recent withdrawl
	private static final int SECOND_RATE_AGE_QUALIFIER = 10; //age, in days, that must be passed before the SECOND_INTEREST_RATE is given
	
	/**
	 * Constructor that produces a MaxiSavingAccount belonging to the passed in Customer
	 * @param owner Customer who the Account belongs to 
	 */
	public MaxiSavingAccount(Customer owner) {
		super(owner);
	}
	
	/**
	 * Method that provides interest daily to the account. Interest rate used depends on the Tranaction history of the account
	 */
	@Override
	public void interestEarned() {
		if (amount > 0) {
			boolean withdrawnRecently = false;
			for (Transaction t : transactions) { //if any transaction which is a withdrawl was made less then 10 days ago
				if (t.amount < 0 && t.tranactionAge() <= SECOND_RATE_AGE_QUALIFIER) {
					withdrawnRecently = true; //say there has been a recent transaction
					break;
				}
			}
			if (withdrawnRecently) { //if theres been a recent transaction use FIRST_INTEREST_RATE
				//double newInterest = Math.floor((amount * InterestRateConversion(FIRST_INTEREST_RATE)) * 10000.0) / 10000.0;
				//interestEarned += newInterest;
				//amount += newInterest;
				//amount = Floor4DP(amount);
				double newAmount = Math.floor((amount * InterestRateConversion(FIRST_INTEREST_RATE)) * 10000.0) / 10000.0;
	    		interestEarned += (newAmount-amount);
	    		amount = newAmount;
			} else { //if there hasn't been a recent transaction use SECOND_INTEREST_RATE
				//double newInterest = Math.floor((amount * InterestRateConversion(SECOND_INTEREST_RATE)) * 10000.0) / 10000.0;
				//interestEarned += newInterest;
				//amount += newInterest;
				//amount = Floor4DP(amount);
				double newAmount = Math.floor((amount * InterestRateConversion(SECOND_INTEREST_RATE)) * 10000.0) / 10000.0;
	    		interestEarned += (newAmount-amount);
	    		amount = newAmount;
			}
		}
	}
	
	/*
	 * Method to calculate interest using the old interest rates amount
	 */
	/*
	@Override
	public void interestEarned() {
		if (amount > THIRD_RATE_START_AMOUNT) {
			double newInterest = Math.floor((amount * InterestRateConversion(THIRD_INTEREST_RATE)) * 10000.0) / 10000.0;
			interestEarned += newInterest;
			amount += newInterest;
			amount = Floor4DP(amount);
	
		} else if (amount > SECOND_RATE_START_AMOUNT) {
			double newInterest = Math.floor((amount * InterestRateConversion(SECOND_INTEREST_RATE)) * 10000.0) / 10000.0;
			interestEarned += newInterest;
			amount += newInterest;
			amount = Floor4DP(amount);
	
		} else {
			double newInterest = Math.floor((amount * InterestRateConversion(FIRST_INTEREST_RATE)) * 10000.0) / 10000.0;
			interestEarned += newInterest;
			amount += newInterest;
			amount = Floor4DP(amount);
		}
		
	}
	*/
	/**
	 * Method that returns the ACCOUNT_TYPE attribute for Customer Statements, Interest Statements and debug
	 * @return String ACCOUNT_TYPE String
	 */
	@Override
	public String getAccountType() {
		return ACCOUNT_TYPE;
	}

}
