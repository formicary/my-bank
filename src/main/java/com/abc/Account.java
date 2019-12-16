package com.abc;

import static java.lang.Math.abs;

import java.util.ArrayList;
import java.util.List;

import com.abc.Transaction.TransactionType;

public abstract class Account {
	
	enum AccountType {
		CHECKING,
		SAVINGS,
		MAXI_SAVINGS
	}

    private List<Transaction> transactions;
    private double balance;
    private double totalInterestEarned;
    // Days is used to calculate interest rate, we need daily compound
    protected static final int DAYS = 365;

    public Account() {
    	this.balance = 0;
    	this.totalInterestEarned = 0;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if(!validAmount(amount)) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        } else {
        	increaseBalance(amount);
            transactions.add(new Transaction(amount, TransactionType.DEPOSIT));
        }
    }

	public void withdraw(double amount) {
		if(balance > 0) {
			if(!validAmount(amount)) {
		        throw new IllegalArgumentException("Amount must be greater than zero");
		    } else {
		    	decreaseBalance(amount);
		        transactions.add(new Transaction(amount, TransactionType.WITHDRAW));
		    }
		}
	}
	
	public void transfer(double amount, Account receiver) {
		if(balance > 0) {
			receiver.receive(amount, this);
			decreaseBalance(amount);
			transactions.add(new Transaction(amount, TransactionType.TRANSFER, this, receiver));
		}
	}
	
	public void receive(double amount, Account sender) {
		increaseBalance(amount);
		transactions.add(new Transaction(amount, TransactionType.RECEIVE, sender, this));
	}
    
	private boolean validAmount(double amount) {
    	if(amount <= 0) return false;
    	return true;
    }
    
    public String getStatement() {
        String statement = "";
        AccountType accountType = getType();
        statement += ("Account Type: " + accountType.toString());
        statement += ('\n');

        for (Transaction t : transactions) {
        	statement += (t.getStatement());
        	statement += ("\n");
        }
        statement += ("Current balance: " + toDollars(balance));
        return statement;
    }
    
    protected void increaseBalance(double amount) {
    	balance += amount;
    }
    
    protected void decreaseBalance(double amount) {
    	balance -= amount;
    }
    
    protected void increaseTotalInterestEarned(double amount) {
    	totalInterestEarned += amount;
    }
    
    public double getBalance() {
    	return this.balance;
    }
    
    public double getTotalInterestEarned() {
    	return this.totalInterestEarned;
    }
    
    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }

    public void gainInterest() {
		double interestEarned = interestEarned();
		increaseBalance(interestEarned);
    }
    
    abstract double interestEarned();
    abstract AccountType getType();
}
