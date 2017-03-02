package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {
	
	//Constant variables
    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;
    public static final double MONEY_ZERO = 0.00;

    private final int accountType;
    private List<Transaction> transactions;
    private int accountNo;
    
    /**
     * The Account constructor defines an account type and a list of transactions
     * @param accountType account type
     * @param transactions list of transactions
     */
	public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

	/**
     * Deposits a new amount into an account.
     * If amount less than or equal to zero, user notified.
     * Otherwise, amount added to ArrayList of transactions
     * and method returns true.
     * @param amount to deposit
     * @return true if transaction successful
     * @return false if transaction unsuccessful
     */
    public boolean deposit(double amount) {
    	boolean result = false;
		if(MONEY_ZERO >= amount) {
			System.out.println("amount to deposit must be greater than zero");
		} else {
			getTransactions().add(new Transaction(amount));
			result = true;
		}
		return result;
    }
    /**
     * Withdraws a new amount from an account.
     * If amount less than or equal to zero, user notified.
     * If total account balance less than amount to withdraw, user notified.
     * Otherwise, negative amount added to ArrayList of transactions
     * and method returns true.
     * @param amount to withdraw
     * @return true if transaction successful
     * @return false if transaction unsuccessful
     */
	public boolean withdraw(double amount) {
		boolean result = false;
	    if (MONEY_ZERO >= amount) {
	        System.out.println("amount to withdraw must be greater than zero");
	    }
	    else {
	    	if (sumTransactions() < amount) {
		    	System.out.println("insufficient funds to withdraw");
	    	}
	    	else {
	    		getTransactions().add(new Transaction(-amount));
	    		result = true;
	    	}
	    }
	    return result;

	}
	/**
	 * Calculates the interest earned for an account type
	 * @return interest earned
	 */
    public double interestEarned() {
        double amount = sumTransactions();
        switch(getAccountType()){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
            case MAXI_SAVINGS:
                if (amount <= 1000)
                    return amount * 0.02;
                if (amount <= 2000)
                    return 20 + (amount-1000) * 0.05;
                return 70 + (amount-2000) * 0.1;
            default:
                return amount * 0.001;
        }
    }
    
    /**
     * Check if list of transactions empty
     * @return true if list of transactions empty
     * @return false if list of transactions populated
     */
    public boolean isTransactionsEmpty() {
    	return getTransactions().isEmpty(); 
    }
    
    /**
     * Sum all transactions to calculate total account balance
     * @return account balance
     */
    public double sumTransactions() {
        double amount = 0.00;
        for (Transaction t: getTransactions()) {
        	amount += t.amount;
        }
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }
    
    public List<Transaction> getTransactions() {
		return transactions;
	}
    
	public int getAccountNo() {
		return accountNo;
	}
	
    public void setAccountNo(int accountNo) {
		this.accountNo = accountNo;
	}	

}
