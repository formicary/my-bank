package com.abc;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;


public class Account {
	
	// Account types for simpler printing
	public enum Type {
		CHECKING ("Checking Account"), SAVINGS ("Savings Account"), MAXI_SAVINGS ("Maxi Savings Account");
		private final String type;
	
	    private Type(final String type) { this.type = type; }
	    public String toString() { return type; }
		
	};
	
    private final Type accountType;
    private List<Transaction> transactions;  
    private double balance;
    private double interestEarned;		// total interest earned on this account
    private LocalDate lastWithdrawal;	// keeps track of last withdrawal for interest rates calculations
    

    public Account(Type accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.balance = 0;
        this.interestEarned = 0;
    }
    
    public Type getAccountType() { return accountType; }    
    public List<Transaction> getTransactions() { return transactions; }    
    public double getBalance() { return balance; }
    public double getTotalInterestEarned() { return interestEarned; }
    
    
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount, Transaction.Type.DEPOSIT));
            this.balance += amount;
        }
    }

	public void withdraw(double amount) {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("amount must be greater than zero");
	    } else if (amount > getBalance()) {
	    	throw new IllegalArgumentException("not enough money on the account");
	    } else {
	    	Transaction t = new Transaction(-amount, Transaction.Type.WITHDRAW);
	        transactions.add(t);
	    	
	        balance -= amount; 
	        lastWithdrawal = t.getDate();
	    }
	}
	
	// transfer from one account to another
	public void transfer(double amount, String id) {
		if (balance+amount < 0) throw new IllegalArgumentException("not enough money on the transfering account");
		
		Transaction t = new Transaction(amount, id);
		balance += amount;
        transactions.add(t);
	}
 
    public String getStatement() {
    	String s = accountType + "\n";
        for (Transaction t : transactions) {
            s += "  " + t.toString() + "\n";
        }     
        s += "Total " + String.format("$%,.2f", balance);
        return s;
    }
    
    // updates balance to include earned interest rates
    public double earnInterest() {
    	double interest = getInterest();
    	balance += interest;
    	interestEarned += interest;
    	transactions.add(new Transaction(interest, Transaction.Type.INTERESTS));
    	return interest;
    }
    
    // interest rates calculation
    public double getInterest() {
        switch(accountType){   
            case SAVINGS:
                if (balance <= 1000)
                    return balance * 0.001;
                else
                    return 1 + (balance-1000) * 0.002;
                
            case MAXI_SAVINGS:      
            	// checks if the last withdrawal was less than 10 days ago
            	boolean withdrawalsInPast10days = (lastWithdrawal==null ? false : 
            		(ChronoUnit.DAYS.between(lastWithdrawal, DateProvider.getInstance().now()) < 10 ? true : false));
            	
                if (withdrawalsInPast10days)
                    return balance * 0.001;
                else
                	return balance * 0.05;
                
            default:
                return balance * 0.001;
        }     
    }

}
