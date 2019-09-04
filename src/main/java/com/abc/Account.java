package com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * Parent class Account which contains the attributes and methods shared by all types of accounts
 * such as deposit, withdraw and transfer
 * 
 * @author Accenture
 * @author Liam Challis
 */
public abstract class Account {
	
	//String used to identify the type of account, used for statements, summarys and debug testing
	public static final String ACCOUNT_TYPE = "Account";
	
	
	public Customer owner; //ref to customer who owns the account
	public double amount; //Amount of money currently in the account
	public List<Transaction> transactions; //List of transactions made by the account
	public double interestEarned; //Amount of money this account has earned with interest, for less calculations during statements
	
	/**
	 * Constructor that creates a new account
	 * @param owner Reference to the customer who owns the account
	 */
	public Account(Customer owner) {
		this.owner = owner;
		transactions = new ArrayList<Transaction>();
	}
	
	/**
	 * Method that takes in a deposit amount and adds to the accounts amount value. Also creates
	 * a transaction recording the action and adds to the accounts transation list
	 * @param depositAmount Amount to be added to the account
	 */
	public void Deposit(double depositAmount) {
		if (depositAmount > 0) {
			amount += depositAmount;
			transactions.add(new Transaction(depositAmount));
		} else {
			 throw new IllegalArgumentException("Must depoisit an amount greater than zero");
		}
		
	}

	/**
	 * Method that takes in a withdrawl amount and subtracts the accounts amount value. Also creates
	 * a transaction recording the action and adds to the accounts transation list
	 * @param depositAmount Amount to be added to the account
	 */
	public void Withdraw(double withdrawAmount) {
		if (withdrawAmount > 0) {
			transactions.add(new Transaction(-withdrawAmount));
			amount -= withdrawAmount; //this is currently ignoring if it goes to negative
		} else {
			 throw new IllegalArgumentException("Must withdraw an amount greater than zero");
		}
	}

	/**
	 * Method that takes in the transfer amount and the target account and transfer the amount to 
	 * the target if valid
	 * @param TransferAmount The amount to withdrawl from the user and deposit to the target
	 * @param targetAccount The account to add the transfer amount to
	 */
	public void Transfer(double TransferAmount, Account targetAccount) {
		if (owner.equals(targetAccount.owner)) {//ensure target account is shared by user
			try {
				//Surround withdraw and deposit in a single try to prevent one only one running correctly
				Withdraw(TransferAmount);
				targetAccount.Deposit(TransferAmount);
			} catch(Exception e) {
				throw new IllegalArgumentException("Must transfer an amount greater than zero");
			}
		} else {
			throw new IllegalArgumentException("Must transfer to account which shares same owner");
		}
	}
	
	/**
	 * Abstract method that all sub-accounts will need to compound interest daily, but will be unique to each subclass
	 */
	public abstract void interestEarned();
	
	/**
	 * Abstract method that all sub-accounts will need to get account type, but will be unique to each subclass
	 * @return String identifying the ACCOUNT_TYPE
	 */
	public abstract String getAccountType();
	
	/**
	 * Method that converts the per-annum interest rate into the daily interest rate.
	 * Counts a year as 365.25 days
	 * @param interestRate The per-annum interest rate to convert
	 * @return interest rate as a daily value
	 */
	public double InterestRateConversion(double interestRate) {
		//return (interestRate/100.0)/365.25; // simple interest converts to daily from annual (365.25days) and from % to true value
		double interestRateValue = 1.0 + (interestRate/100.0);
		return (Math.pow(interestRateValue, (1.0/365.0))); //compound daily rate daily = yearly^(1/365)
	}
	
	/**
	 * Method that floors a double to 4 significant figures
	 * @param value Double value to floor to 4 S.F
	 * @return Value floored to 4 S.F
	 */
	public double Floor4DP(double value) {
		 return Math.floor(value * 10000.0) / 10000.0;
	}

}
