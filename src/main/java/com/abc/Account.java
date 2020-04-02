package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Account {

    public List<Transaction> transactions;
    public double capital;

    public Account() {
        this.transactions = new ArrayList<Transaction>();
        this.capital = 0;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount, Transaction.DEPOSIT));
            this.capital += amount;
        }
    }

	public void withdraw(double amount) {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("amount must be greater than zero");
	    } else if (amount <= this.capital) {
	    	this.capital -= amount;
	        transactions.add(new Transaction(-amount, Transaction.WITHDRAW));
	    } else {
	    	throw new IllegalArgumentException("cannot withdraw more than account capital");
	    }
	}

	public void transfer(Account recieving, double amount) {
		this.withdraw(amount);
		recieving.deposit(amount);
	}
	
    public double interestEarned() {
    	return Double.valueOf(0.0);
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
    
    public Date lastWithdrawDate() {
    	for (int i = this.transactions.size() - 1; i >= 0; i--) {
    		if (transactions.get(i).type == Transaction.WITHDRAW) {
    			return transactions.get(i).getTransactionDate();
    		}
    	}
    	return null;
    }

	public double getCapital() {
		return capital;
	}

	public void setCapital(double capital) {
		this.capital = capital;
	}
    
}
