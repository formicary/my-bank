package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public abstract class Account {
	
	private List<Transaction> transactions = new ArrayList<Transaction>();
    private double balance = 0.00;
    protected Customer account_holder;
    private final Date account_opendate = DateProvider.getInstance().past(365);
    protected Date latest_interestdate = account_opendate;
    private double earned_interest = 0.00;
    
    public boolean deposit(double amount) {
    		if (transactions.size() != 0) updateBalance();
    		
    		if (amount <= 0) { 		
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
        		balance += amount;
            transactions.add(new Transaction(amount));
            return true;
        }
    }

	public boolean withdraw(double amount) {
		updateBalance();
	    if (amount <= 0) {
	        throw new IllegalArgumentException("amount must be greater than zero");
	    } else {
	    		if(balance>=amount) {
	    			balance -= amount;
	    			transactions.add(new Transaction(-amount));
	    			return true;
	    		}else {
	    			return false;
	    		} 		
	    }
	}

	protected abstract double interestEarned();

    public boolean checkIfTransactionsExist(Transaction transaction) {
    		for (Transaction t :transactions) {
    			if(t == transaction) return true;
    		}
    		return false;
    }
    
    public void updateBalance() {
    		double temp_int = interestEarned();
    		balance += temp_int;
    		earned_interest += temp_int;
    }
    
    public double getAccountBalance() {
    		return balance;
    }
    
    public String getAccountHolder() {
    		return account_holder.getName();
    }
    
    public List<Transaction> getTransactions(){
    		return transactions;
    }
    
    public double getinterest() {
    		updateBalance();
    		return earned_interest;
    }
    
    protected boolean checkwithdrawlinpasttendays() {
    		Date now = DateProvider.getInstance().now();
    		
    		for (Transaction t: transactions){
    			if (t.getamount()<0 && now.getTime()-t.getTransactionDate().getTime()<864000000) {
    				return true;
    			}else continue;
    		}
    		
    		return false;
    }

}
