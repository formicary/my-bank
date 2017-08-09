package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abc.interestCalculation.InterestCalculator;

public class Account {


    private final AccountType accountType;
    public List<Transaction> transactions;
    
    private double balance;
    
    private double totalInterestEarned; 

    
 
    public Account(AccountType accountType) {
    	switch(accountType){
    		case CHECKING: break;
    		case SAVINGS: break;
    		case MAXI_SAVINGS: break;
    		default: throw new IllegalArgumentException("Invalid accoount type");
    	}
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.balance = 0.0;
        this.totalInterestEarned = 0.0;
    }

    
    public void deposit(double amount) {
        if (amount <= 0) {
        	
            throw new IllegalArgumentException("Amount must be greater than zero");
        
        }
        else {
        	this.updateBalance();
            transactions.add(new Transaction(amount, TransactionType.DEPOSIT));
            this.balance+=amount;
        
        }
    }

    
	public void withdraw(double amount) {
		if (amount <= 0) {
			
			throw new IllegalArgumentException("Amount must be greater than zero");
		
		} else if (this.balance - amount < 0.0) {
			
			throw new IllegalArgumentException("Account balance not enough to make withdrawal"
					+ " only " + this.balance + " left");
		
		} else {
			this.updateBalance();
			transactions.add(new Transaction(-amount, TransactionType.WITHDRAWAL));
			this.balance -= amount;
		
		}
	}

	
    public double interestEarned() {
    	this.updateBalance();
    	return this.totalInterestEarned;
    }
    
    
    public double calculateInterest(){
        InterestCalculator calc = new InterestCalculator();
        return calc.calculateInterest(this);
    }

    
    public double sumTransactions() {
    	
    	if (this.transactions.isEmpty()) return 0.0;
    	
        double amount = 0.0;
        
        for (Transaction t: transactions)
            amount += t.getAmount();
        
        return amount;
    }

    
    public AccountType getAccountType() {
        return accountType;
    }
    
    public double getCurrentBalance(){
    	return this.balance;
    }
    
    public double getUpdatedCurrentBalance(){
    	this.updateBalance();
    	return this.balance;
    }
   
    
    public Transaction getLastTransactionByType(TransactionType t){
    	Transaction lastTransaction = new Transaction(0, TransactionType.EMPTY);
    	
    	for(int i=this.transactions.size()-1; i>-1; i--)
    	{    	
    		if(this.transactions.get(i).getType()==t){
    			lastTransaction = this.transactions.get(i);
    			break;
    		}
    	}
    	
    	return lastTransaction;
    }
    
    
    public Transaction getLastTransaction(){
    	
    	Transaction lastTransaction = new Transaction(0, TransactionType.EMPTY);
    	
    	if(!this.transactions.isEmpty()){
    		lastTransaction = this.transactions.get(this.transactions.size() - 1);
    	}
    	
    	return lastTransaction;
    }
    
    
	public long getDaysSinceLastTransaction() {
		
		Transaction lastTransaction = this.getLastTransaction();
		
		if (lastTransaction.getType() == TransactionType.EMPTY) {
			
			return 0; // Better to throw a "NoTransactionsException"
			
		} else {
			
			DateProvider dp = new DateProvider();
			Date lastTransactionDate = lastTransaction.getDate();
			Date today = dp.now();
			
			return dp.daysAppart(today, lastTransactionDate);
		
		}
	}
    
	
	public void updateBalance() {
		double interest = this.calculateInterest();
		this.transactions.add(new Transaction(interest, TransactionType.INTEREST_EARNED));
		this.totalInterestEarned += interest;
		this.balance += interest;
	}

}
