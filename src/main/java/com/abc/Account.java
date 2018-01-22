package com.abc;

import java.util.*;

public class Account {    
    private AccountType accountType;
    protected List<Transaction> transactions;
    
    protected Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }
    
    // Allowing only positive amounts to be deposited.
    // Returns True/False depending on Success/Failure
    protected boolean deposit(double amount) {
    	try {
    		if (amount <= 0)
    			throw new IllegalArgumentException("Amount must be greater than zero");    			
    		else {
    			// if not empty, Adding interest earned since last transaction before depositing
    			if(!transactions.isEmpty())
    				updateAmount();
    			transactions.add(new Transaction(amount, TransactionType.DEPOSIT));
    			return true;
    		}
    			
    	}
    	catch(Exception e) {
    		System.out.println(e);
    		return false;
    	}
    }
    
    // helper method for testing
    protected boolean depositWithdraw(Transaction t) {
    	transactions.add(t);
    	return true;
    }
    
    // Only positive amounts or amounts less/equal to account balance can be withdrawn   
    // Returns True/False depending on Success/Failure
    protected boolean withdraw(double amount) {
    	try {
    		if (amount <= 0) 
    			throw new IllegalArgumentException("Amount must be greater than zero");
    		else {
    			// if not empty, Adding interest earned since last transaction before withdrawing
    			if(!transactions.isEmpty())
    				updateAmount();
    			if(sumTransactions() - amount < 0.0)
    				throw new Exception("Specified amount cannot be withdrawn. Insufficient Balance");
    			else {
    				transactions.add(new Transaction(-amount, TransactionType.WITHDRAW));
    				return true;
    			}    				
    		}
    	} 
    	catch(Exception e) {
    		System.out.println(e);
    		return false;
    	}
    }
    
    // to add to transaction if interest > 0 
    // called before withdraw, deposit, and when displaying interest earned to update the amount after compounding daily
    protected void updateAmount() {
    	double amount = sumTransactions();
    	double interest = 0.0;
    	
    	switch(accountType) {
        case SAVINGS: 									// rate of 0.1% for first 1000 then 0.2%
            if (amount <= 1000)
                interest = compoundInterest(amount, 0.001);
            else
                interest = compoundInterest(amount, 0.002);
            break;
            
        case MAXI_SAVINGS: 								// 5% assuming no withdrawals in the past 10 days otherwise 0.1%
            if (DateProvider.daysTillNow(lastWithdrawalDate()) < 10)
            	interest = compoundInterest(amount, 0.001);
            else
            	interest = compoundInterest(amount, 0.05);
            break;
        default:										// basic rate of 0.1% for Checking Account
            interest = compoundInterest(amount, 0.001);
    	}
        if (interest > 0.0)
        	transactions.add(new Transaction(interest, TransactionType.ADDEDINTEREST));
    }
    	
    // calculating total interest earned for the account from list of transactions
    protected double interestEarned() {
        double interest = 0.0;
        if(!transactions.isEmpty())
			updateAmount();
        for (Transaction t: transactions)
        	if(t.getTransactionType() == TransactionType.ADDEDINTEREST)
        		interest += t.getAmount();
        return interest;
    }
    
    // to calculate the compound interest since last transaction 
    protected double compoundInterest(double present, double interest) {
    	double ci = 0.0;
    	// since compounded daily, n = number of days since last transaction, interest/365
    	long n = daysSinceLastTransaction();
    	ci = present * (Math.pow((1 + interest/365), n) - 1);
    	return ci;
    }
    
    protected long daysSinceLastTransaction() {
    	if(!transactions.isEmpty())
    		return DateProvider.daysTillNow(transactions.get(transactions.size()-1).getDate());
    	else
    		return 0;
    }
    
    // returns last date of withdrawal if withdrawn, otherwise first deposit. 
    protected Date lastWithdrawalDate() {
    	int i = transactions.size() - 1;
    	while(i >= 0) {
    		// if withdrawn then, return last withdraw date else return first deposit date
    		if(transactions.get(i).getTransactionType() == TransactionType.WITHDRAW)
    			return transactions.get(i).getDate();
    		if(i == 0)
    			break;
    		i--;
    	}
    	return transactions.get(i).getDate(); 
    }
    
    // TOTAL account balance
    protected double sumTransactions() { 
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.getAmount();
        return amount;
    }
    
    // getter method to access account type.
    public AccountType getAccountType() {
        return accountType;
    }

}
