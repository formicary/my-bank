package com.abc.account;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.abc.transaction.Deposit;
import com.abc.transaction.Transaction;
import com.abc.transaction.Withdraw;

abstract class Account implements AccountI {
	
	private List<Transaction> transactions;
		
	public abstract double interestEarned();
	
	public Account() {
    	
        this.setTransactions(new ArrayList<Transaction>());
        
    }
	
	
	public List<Transaction> getTransactions() {

		return transactions;
	}
	
	
	public void setTransactions(List<Transaction> transactions) {
		
		this.transactions = transactions;
	}

	
    public void deposit(double amount) {
    	
        if (amount <= 0) {
        	
            throw new IllegalArgumentException("amount must be greater than zero");
            
        } else {
        	
            transactions.add(new Deposit(amount));
        }
    }

    
    public void withdraw(double amount) {
    	double balance = sumTransactions();
    	if (amount<balance){
		    if (amount <= 0) {
		        throw new IllegalArgumentException("amount must be greater than zero");
		    } else {
		    	transactions.add(new Withdraw(-amount));
		    }
    	}else{
				throw new NegativeAmountException("The amount requested exceed available balance");
			 
	    	
	    }
	}

    
    public double sumTransactions() {
    	
    	double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.getAmount();
        return amount;
    }

    
    
    
    public void transferFund(Account from,Account to,double amount) throws NegativeAmountException{
    	
    	double balance = from.sumTransactions();
    	if (amount<balance){
    		from.withdraw(amount);
        	to.deposit(amount);
    		
    	}else{
    		throw  new NegativeAmountException("The amount requested exceed available balance");
    	}
    	
    		
    	
    }
    public boolean checkforWithdrawals(int numberOfDays,int type){
    	
    	Calendar today =Calendar.getInstance();
    	today.roll(Calendar.DATE, -numberOfDays);
    	
    	
        for (Transaction t: transactions)
        	 
        	if (t.getTransactionDate().before(today.getTime())||t instanceof Withdraw){
        		return false;	
        	}
        return true;
    }
    

	
    
   
    
}
