package com.mybank;

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
    	
    	double interestEarned = 0.0;

    	//If no transactions have been made, then the interest is zero
    	//regardless of account type
    	if (transactions.isEmpty()) {
    		return interestEarned;
    	}
    	
    	double currentAmount = 0.0;
    	
    	//Accruing daily interest based on daily amount in account
    	for (int i = 0; i < transactions.size(); ++i) {
    		long numDays = 0;
    		//Calculate with same amount until next transaction or current day
    		if (i < transactions.size()-1)
    			numDays = getDateDiff(transactions.get(i).getTransactionDate(), transactions.get(i+1).getTransactionDate());
    		else {
    			numDays = getDateDiff(transactions.get(i).getTransactionDate(), DateProvider.getInstance().now());
    			
    			//Accruing the daily interest for the current day
    			if (numDays == 0)
    				numDays = 1;
    		}

    		currentAmount += transactions.get(i).getTransactionAmount();

        	//Calculate interest differently based on account type
	        switch(accountType){
	            case SAVINGS:
		        	//Rate of 0.1% for the first $1,000 then 0.2% for the rest
                    if (currentAmount <= 1000)
                    	interestEarned += (currentAmount * 0.001)/365 * numDays;
                    else
                    	interestEarned +=  (1 + (currentAmount-1000) * 0.002)/365 * numDays;
                    break;
	            case SUPER_SAVINGS:
	            	//Rate of 2% for the first $1,000 then 5% for the next $1,000 then 10% for the rest
	                if (currentAmount <= 1000)
                    	interestEarned += (currentAmount * 0.02)/365 * numDays;
	                else if (currentAmount <= 2000)
                    	interestEarned +=  (20 + (currentAmount-1000) * 0.05)/365 * numDays;
	                else
                    	interestEarned +=  (70 + (currentAmount-2000) * 0.1)/365 * numDays;	                
                    break;
	            case MAXI_SAVINGS:
	            	//Rate of 5% assuming no withdrawals in the past 10 days otherwise 0.1%
	            	Transaction lastWithdrawal = getLastWithdrawal();
	            	if (lastWithdrawal != null) {
		            	if (getDateDiff(lastWithdrawal.getTransactionDate(), DateProvider.getInstance().now()) <= 10) {
		            		interestEarned += (currentAmount * 0.001)/365 * numDays;
		            		break;
		            	}
	            	}
            		interestEarned += (currentAmount * 0.05)/365 * numDays;
                    break;
	            default:
	            	//Flat rate of 0.1% for Checking account
            		interestEarned += (currentAmount * 0.001)/365 * numDays;
                    break;
	        }
    	}
    	
    	return interestEarned;
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
