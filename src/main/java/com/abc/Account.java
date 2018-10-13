package com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Accenture, rrana
 * @version v2.0
 *
 */
public interface Account {
	
	/**
	 * 
	 * @return the current balance of this account
	 */
	public double getBalance();
    
	/**
	 * Deposit amount
	 * @param amount the amount to be deposited
	 */
    public void deposit(double amount);

    /**
     * Withdraw amount
     * @param amount the amount to be withdrawn
     */
    public void withdraw(double amount);

    /**
     * Get interest earned
     * @return earned the interest earned
     */
    public double interestEarned();

    /**
     * @return the sum of all transaction
     */
    public double sumTransactions();

    /**
     * @return the type of account
     */
    public String getAccountType();
    
    /**
     * 
     * @return The list of transaction on this account
     */
    public ArrayList<Transaction> getTransactions();

    /**
     * Transfer a given amount to any given account or receive an amount 
     * @param account the account to which to transfer the amount
     * @param balance the amount to be transferred
     * @param isSent true if the amount is sent, false if it is received
     */
    public boolean transferAmount(Account account, double amount, boolean isSent);
}
