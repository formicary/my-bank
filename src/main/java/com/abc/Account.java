package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.Period;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    private ArrayList<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }
    
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {
    	if (amount <= 0) {
    		throw new IllegalArgumentException("amount must be greater than zero");
    	} else {
    		transactions.add(new Transaction(-amount));
    	}
    }
    
    public List<Transaction> getTransactions() {
    	return transactions;
    }

    public double interestEarned() {
        double amount = getBalance();
        double interestEarned = 0.0;
        
        switch(accountType) {
            case SAVINGS:
                if (amount <= 1000) {
                    interestEarned = amount * 0.001;
                } else  {
                    interestEarned =  1.0 + (amount-1000) * 0.002;
                }
                break;
            case MAXI_SAVINGS:
            	if (!withdrawalsDone()) {
            		interestEarned = amount * 0.05;
            	} else {
            		interestEarned = amount * 0.001;
            	}   
            	break;
            default: 
            	interestEarned = amount * 0.001;
                break;
        }
        return interestEarned;
    }

    public boolean withdrawalsDone() {
    	boolean withdrawalsDone = false;
    	// get current date
    	for (int i= transactions.size() - 1; i > -1; i--) {
    		Transaction t = transactions.get(i);
    		LocalDate nowDate = LocalDate.now();
    		LocalDate transactionDate = t.getDate();    		
		    Period timePeriod = Period.between(transactionDate, nowDate);
		    int numDays = timePeriod.getDays();
    		if (t.getAmount() < 0) { // has been a withdrawal
    		    if (numDays <= 10) { // in last 10 days
    		    	withdrawalsDone = true;
    		    	break;
    		    }
    		}
    		if (!(withdrawalsDone)) {
    			if (numDays > 10) {
    				break; // no need to keep searching
    			}
    		}
    	}    	
    	return withdrawalsDone;
    }
    
    public double getBalance() {
    	double balance = 0.0;
        for (Transaction t: transactions)
            balance += t.getAmount();
        return balance;
    }
    
    public int getAccountType() {
        return accountType;
    }

}
