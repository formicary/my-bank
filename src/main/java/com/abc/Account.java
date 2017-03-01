package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;
    public static final double MONEY_ZERO = 0.00;

    private final int accountType;
    private List<Transaction> transactions;
    private int accountNo;
    
    /**
     * The Account constructor defines an account type and a list of transactions (deposit or withdrawal)
     * @param accountType account type
     * @param transactions list of transactions
     */
	public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }
    /**
     * deposit takes a new amount of type double.
     * If the amount is less than or equal to zero, an exception is raised.
     * Otherwise, the amount is added to ArrayList of transactions.
     * @param amount to be added
     */
    public void deposit(double amount) {
    	
		if(MONEY_ZERO >= amount) {
			System.out.println("amount to deposit must be greater than zero");
		} else {
			getTransactions().add(new Transaction(amount));
		}		
    }
    /**
     * withdraw takes a new amount of type double.
     * If the amount is less than or equal to zero, an exception is raised.
     * Otherwise, the amount is taken from the ArrayList of transactions.
     * @param amount to be withdrawn
     */
	public void withdraw(double amount) {
	    if (MONEY_ZERO >= amount) {
	        System.out.println("amount to withdraw must be greater than zero");
	    } 
	    else if (sumTransactions() < amount) {
	    	System.out.println("insufficient funds to withdraw");
	    }	    
	    else {
	        getTransactions().add(new Transaction(-amount));
	    }
	}
	/**
	 * This method calculates the interest earned for an account type
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
    
    public boolean isTransactionsEmpty() {
    	return getTransactions().isEmpty(); 
    }

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

}
