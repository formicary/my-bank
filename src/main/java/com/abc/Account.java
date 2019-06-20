package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;

    public Account(int accountType) {
    	if (!isValidAccountType(accountType)) {
    		throw new IllegalArgumentException("Invalid account type");
    	}
    	
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
        
        transactions.add(new Transaction(amount));
    }

	public void withdraw(double amount) {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("amount must be greater than zero");
	    }
	    else if (sumTransactions() <= 0) {
	    	throw new IllegalArgumentException("not enough in account to make withdrawal");
	    }
	    
        transactions.add(new Transaction(-amount));
	}

    public double interestEarned() throws Exception {
        double amount = sumTransactions();
        
        switch(accountType) {        
	        case CHECKING:
	        	return amount * 0.001;
	        	
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                return 1 + (amount-1000) * 0.002;
                
            case MAXI_SAVINGS:
            	if (daysSinceLastWithdrawal() < 10)
            		return amount * 0.001;
            	return amount * 0.05;
        }
        //If the account type is not one of the predefined values
        throw new Exception("Invalid account type");
    }
    
    public int daysSinceLastWithdrawal() {
    	long lastWithdrawalTime = 0;
    	
    	//For every transaction
    	for (Transaction transaction : transactions) {
    		//If it is a withdrawal
    		if (transaction.amount < 0) {
    			long transactionTime = transaction.transactionDate.getTime();
    			
    			//If it happened more recently than the current most recent transaction
    			if (transactionTime > lastWithdrawalTime) {
    				lastWithdrawalTime = transactionTime;
    			}
    		}
    	}
    	
    	long currentTime = DateProvider.getInstance().now().getTime();
    	
    	return (int) ((currentTime - lastWithdrawalTime) / (24 * 60 * 60 * 1000));
    }

    public double sumTransactions() {
    	double amount = 0.0;
    	
        for (Transaction transaction: transactions) {
            amount += transaction.amount;
        }
        
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }
    
    //accountType must be a valid number
    public boolean isValidAccountType(int accountType) {
    	if (accountType <= 2 && accountType >= 0) {
    		return true;
    	}
    	return false;
    }

}
