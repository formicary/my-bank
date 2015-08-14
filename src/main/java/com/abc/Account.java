package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.Date;

public class Account {

	public enum AccountType{
		CHECKING("Checking Account"),
		SAVINGS("Savings Account"),
		SUPER_SAVINGS("Super-Savings Account"),
		MAXI_SAVINGS("Maxi-Savings Account");
		
		private final String accountDesc;
		
		private AccountType(String accountDesc) {
	        this.accountDesc = accountDesc;
	    }
		
		public String toString() {
	        return accountDesc;
	    }
	}
	
	private final AccountType accountType;
    public List<Transaction> transactions;
    private double accountTotal;

    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.accountTotal = 0.0;
    }

    public void deposit(double amount) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        } else {
            transactions.add(new Transaction(amount));
            accountTotal += amount;
        }
    }

	public void withdraw(double amount) throws IllegalArgumentException {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("Amount must be greater than zero.");
	    } else if (amount > accountTotal){
	        throw new IllegalArgumentException("Insufficient funds in account!");
	    } else {
	        transactions.add(new Transaction(-amount));
	        accountTotal -= amount;
	    }
	}

    public double getInterestEarned() {
    	// Calculate interest differently based on account type
        switch(accountType){
            case SAVINGS:
            	//Rate of 0.1% for the first $1,000 then 0.2% for the rest
                if (accountTotal <= 1000)
                    return accountTotal * 0.001;
                else
                    return 1 + (accountTotal-1000) * 0.002;
            case SUPER_SAVINGS:
            	//Rate of 2% for the first $1,000 then 5% for the next $1,000 then 10% for the rest
                if (accountTotal <= 1000)
                    return accountTotal * 0.02;
                if (accountTotal <= 2000)
                    return 20 + (accountTotal-1000) * 0.05;
                return 70 + (accountTotal-2000) * 0.1;
            case MAXI_SAVINGS:
            	//Rate of 5% assuming no withdrawals in the past 10 days otherwise 0.1%
            	Transaction lastWithdrawal = getLastWithdrawal();
            	if (lastWithdrawal != null) {
	            	if (getDateDiff(lastWithdrawal.getTransactionDate(), DateProvider.getInstance().now()) <= 10)
            			return accountTotal * 0.001;
            	}
    			return accountTotal * 0.05;
            default:
            	//Flat rate of 0.1% for Checking account
                return accountTotal * 0.001;
        }
    }

    private long getDateDiff(Date date1, Date date2) {
        long diffInMilli = date2.getTime() - date1.getTime();
        return TimeUnit.DAYS.convert(diffInMilli, TimeUnit.MILLISECONDS);
    }
    
    private Transaction getLastWithdrawal() {
    	int index = -1;

    	if (!transactions.isEmpty()) {
        	//Check all transactions, starting at the end of the list, to find the latest withdrawal
	    	for (int i = transactions.size()-1; i >= 0; --i) {
	    		if (transactions.get(i).getTransactionType().equals("Withdrawal")) {
	    			index = i;
	    			break;
	    		}
	    	}
    	}

    	//If no withdrawals are found, return null
    	if (index == -1)
    		return null;

    	return transactions.get(index);
    }
    
    public double getAccountTotal() {
        return accountTotal;
    }

    public AccountType getAccountType() {
        return accountType;
    }

}
