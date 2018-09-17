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

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount, Transaction.DEPOSIT));
        }
    }

	public void withdraw(double amount) {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("amount must be greater than zero");
	    } else {
	        transactions.add(new Transaction(-amount, Transaction.WITHDRAW));
	    }
	}
	
	public void transfer(double amount, Account account) {
		// Money goes from the account the transfer method is called on
		// and moved to the account specified in the method parameters.
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			account.transactions.add(new Transaction(amount, this));
			transactions.add(new Transaction(-amount, account));
		}
	}

    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType) {
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount - 1000) * 0.002;
		case MAXI_SAVINGS:
                for (Transaction t: transactions) {
                	if (t.getTransactionType() == Transaction.WITHDRAW) {
                		Date now = DateProvider.getInstance().now();
                		Date transactionDate = t.getTransactionDate();                
                		
                		long msDiff = Math.abs(now.getTime() - transactionDate.getTime());
                		long daysDiff = TimeUnit.DAYS.convert(msDiff, TimeUnit.MILLISECONDS);

                		if (daysDiff < 10)
                			return amount * 0.0001;
                	}
                }
                return amount * 0.05;
		default:
                return amount * 0.001;
        }
    }

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }
    
    public String toString(int accountType) {
    	switch(accountType) {
    		case SAVINGS:
    			return "Savings Account";
    		case MAXI_SAVINGS:
    			return "Maxi Savings Account";
    		default:
    			return "Checking Account";			
    	}
    }

}
