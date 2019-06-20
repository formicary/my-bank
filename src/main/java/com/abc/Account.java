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
        else {
            transactions.add(new Transaction(amount));
        }
    }

	public void withdraw(double amount) {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("amount must be greater than zero");
	    }
	    else {
	        transactions.add(new Transaction(-amount));
	    }
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
                if (amount <= 1000)
                    return amount * 0.02;
                if (amount <= 2000)
                    return 20 + (amount-1000) * 0.05;
                return 70 + (amount-2000) * 0.1;
        }
        //If the account type is not one of the predefined values
        throw new Exception("Invalid account type");
    }

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }
    
    //TODO - Is this necessary? Just put into sumTransactions?
    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
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
