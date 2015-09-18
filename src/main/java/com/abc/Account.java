package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Account {

    public enum AccountType {
    	CHECKING(0), SAVINGS(1), MAXI_SAVINGS(2);
    	
		@SuppressWarnings("unused")
		private int value;
		private AccountType(int value) {
			this.value = value;
		}
	}

    private final AccountType accountType;
    public List<Transaction> transactions;

    public Account(AccountType accountType) {
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

    public double interestEarnedOnDay(Date d) {
    	double amount = sumTransactions(d);
        switch(accountType){
        	case CHECKING:
        		return amount * (0.001 / 365);
        	case SAVINGS:
                if (amount <= 1000)
                    return amount * (0.001 / 365);
                else
                    return amount * (0.001 / 365) + (amount-1000) * (0.002 / 365);
            case MAXI_SAVINGS:
            	for(int i = transactions.size() - 1; i >= 0; i--) {
	        		int diff = transactions.get(i).fromCreationToDateInDays(d);
	        		// interest rate of 5% assuming no withdrawals in the past 10 days otherwise 0.1%
	            	if (transactions.get(i).amount < 0) {
	            		if(diff <= 10 && diff >= 0)
	            			return amount * (0.001 / 365);
	            	}
                }
                return amount * (0.05 / 365);
            default:
                throw new RuntimeException("account type not recognized");
        }
    }

    public double sumTransactions() {
        return sumTransactions(Calendar.getInstance().getTime());
    }

    // calulcate the balance of the account for a specific day
    public double sumTransactions(Date d) {
        double amount = 0.0;
        for (Transaction t: transactions) {
        	if(t.fromCreationToDateInDays(d) >= 0);
        		amount += t.amount;
        }
        return amount;
    }

    public String getAccountType() {
		switch(accountType){
            case CHECKING:
                return "Checking Account";
            case SAVINGS:
            	return "Savings Account";
            case MAXI_SAVINGS:
            	return "Maxi Savings Account";
            default:
                throw new RuntimeException("account type not recognized");
        }
	}
}
