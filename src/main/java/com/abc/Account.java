package com.abc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public double interestEarned() {
        double amount = sumTransactions();
        
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
            case MAXI_SAVINGS:
            	Transaction t = this.getLastWithdrawal();
            	
            	if (t != null) {
            		if (LocalDateTime.now().minusDays(10).isBefore(t.getTransactionDate()))
                    	return amount * 0.001;
            	}
            	
                return amount * 0.05;
            default:
                return amount * 0.001;
        }
    }

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        
        for (Transaction t: transactions)
            amount += t.amount;
        
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }
    
    public Transaction getLastWithdrawal() {
    	if (transactions.size() < 1)
    		throw new IllegalArgumentException("no transactions have been made");
    	
    	for (int i = transactions.size() - 1; i > 0; i--) {
    		if (transactions.get(i).amount < 0) {
    			return transactions.get(i);
    		}
    	}
    	
    	return null;
    }
}
