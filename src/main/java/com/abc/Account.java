package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;
    private Date lastWithdrawal;   
    private Date lastAccruement;   

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        // interest does not start accruing until after the account is opened
        this.lastAccruement = DateProvider.getInstance().now();
    }

    public void deposit(double amount) {
    	// accrue daily interest before making deposit
    	accrueInterest();
    	if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount, Transaction.DEPOSIT));
        }
    }

	public void withdraw(double amount) {
		// accrue daily interest before making withdrawal
		accrueInterest();
		if (amount <= 0) {
	        throw new IllegalArgumentException("amount must be greater than zero");
	    } else {
	    	// Assumption: no debt
	    	// cannot withdraw if account does not contain enough
	    	if (sumTransactions() - amount >= 0) {
	    		transactions.add(new Transaction(-amount, Transaction.WITHDRAW));
	    		lastWithdrawal = DateProvider.getInstance().now();
	    	}
	    	else {
	    		throw new IllegalArgumentException("insufficiant funds");
	    	}
	    }
	}

    public double interestEarned(boolean paid) {	
        double amount;
        
        if (paid) {
        	amount = 0.0;
        	//Add new interest in first
        	accrueInterest();       
        	for (Transaction t: transactions)
        		if (t.getTransactionType() == Transaction.INTEREST) {
        			amount += t.getTransactionAmount();
        		} 		
            return amount;
        }
        else {
        	amount = sumTransactions();
        	
	        switch(accountType){
	        	case CHECKING:
	        		return amount * 0.001;        
	        	case SAVINGS:
	                if (amount <= 1000)
	                    return amount * 0.001;
	                else
	                    return 1 + (amount-1000) * 0.002;
	            case MAXI_SAVINGS:
	            	/*
	                if (amount <= 1000)
	                    return amount * 0.02;
	                if (amount <= 2000)
	                    return 20 + (amount-1000) * 0.05;
	                return 70 + (amount-2000) * 0.1;
	                */
	            	// last withdrawal never occurred or was 10 days ago. Use lazy OR as last withdrawal may be null 
	            	if (lastWithdrawal == null || lastWithdrawal.before(DateProvider.getInstance().daysAgo(10)))
	            		return amount * 0.05;
	            	else
	            		return amount * 0.01;
	            default:
	            	throw new IllegalArgumentException("account type doesn't exist");
	        }
        }
    }

    /*
     * 2 functions when one is simply calling the other
    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }
    */
    
    // replacement function
    public double sumTransactions() {
    	double amount = 0.0;
        
    	for (Transaction t: transactions)
            amount += t.getTransactionAmount();
        return amount;
     }
    
    public int getAccountType() {
        return accountType;
    }
    
    // Called on each access of account, e.g. statement, withdraw, etc. 
    private void accrueInterest()
    {
    	Date today = DateProvider.getInstance().now();
    	// Calculate days since last accrued interest
    	int daysPast = (int) TimeUnit.DAYS.convert(today.getTime()-lastAccruement.getTime(), TimeUnit.MILLISECONDS);
    	
    	// Assumption: interest paid compound is paid the same rate 
    	for (int i = 0; i < daysPast; i++) {
    		transactions.add(new Transaction(interestEarned(false), Transaction.INTEREST));
    	}   
    	lastAccruement = today;
    }
    
    //Testing purposes
    //Set lastWithdrawal date
    public void setLastWithdrawalDate (int daysAgo) {
    	lastWithdrawal = DateProvider.getInstance().daysAgo(daysAgo);
    }
    
    //Set lastAccruement date
    public void setLastAccruementDate (int daysAgo) {
    	lastAccruement = DateProvider.getInstance().daysAgo(daysAgo);
    }

}