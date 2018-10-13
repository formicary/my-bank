package com.abc;

import java.util.ArrayList;

import static java.lang.Math.abs;

/**
 * Customer
 * @author Accenture, rrana
 * @version 2.0
 */
public interface Customer {
	
	/**
	 * 
	 * @return Name of the customer
	 */
	public String getName();
	
	/**
	 * Add new account
	 * @param account the account to be added
	 */
    public void addAccount(Account account);

    /**
     * 
     * @return number of accounts owned by this customer
     */
    public int getNumberOfAccounts();

    /**
     * 
     * @return total interest earned by this customer in all owned accounts
     */
    public double totalInterestEarned();

    /**
     * 
     * @return the statement
     */
    public String getStatement();
    
    /**
     * 
     * @return the list of all accounts owned by this customer
     */
    public ArrayList<Account> getAccounts();
}
