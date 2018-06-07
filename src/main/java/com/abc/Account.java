package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

import java.time.*;
import java.time.temporal.ChronoUnit;

public class Account {

	//Define Account Types
	public enum Type {
		CHECKING,
		SAVINGS,
		MAXI_SAVINGS
	}

	//Define Account Fields
    private final Type accountType;
    private List<Transaction> transactions;

    //Account Constructor
    public Account(Type accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }
    
    //Return the Account's Type
    public Type getAccountType() {
        return accountType;
    }
    
    //Return Statement list of Transactions
    public String getAccountStatement() {
    	String statement = "";
    	for (Transaction t : transactions) {
    		statement += "  " + (t.getAmount() < 0 ? "withdrawal" : "deposit") + " " + StatementUtil.toDollars(t.getAmount(), false) + "\n";
        }
    	if(transactions.size() > 0) {
    		statement += "  interest " + StatementUtil.toDollars(totalInterest(), false)  + "\n";	
    	}
    	return statement;
    }

    //Deposit a specified amount into the account
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
        }
    }

    //Withdraw a specified amount into the account
	public void withdraw(double amount) {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("Amount must be greater than zero");
	    }
	    else {
	        transactions.add(new Transaction(-amount));
	    }
	}
	
	//Return Total Interest Accrued
	public double totalInterest() {
		double amount = 0.0;
		double balance = 0.0;
        double dateDiff = 0.0;
        for (int i = 1; i <= transactions.size(); i++) {
        	
        	if(i == transactions.size()) {
        		LocalDateTime lastTrans = LocalDateTime.from(transactions.get(i-1).getDateTime());
        		dateDiff = lastTrans.until(LocalDateTime.now(), ChronoUnit.DAYS);
        	}
        	else {
        		Transaction t = transactions.get(i);
        		LocalDateTime lastTrans = LocalDateTime.from(transactions.get(i-1).getDateTime());
        		dateDiff = lastTrans.until(t.getDateTime(), ChronoUnit.DAYS);
        	}
        	
        	double lastAmount = transactions.get(i-1).getAmount(); 
        	balance += lastAmount;
        	
        	//Check for Positive Account Balance
        	if(balance > 0) {
        		amount += interestEarned((balance + amount), dateDiff);
        	}
        }
        return(amount);
	}
	
	//Calculate interest earned on given amount
    private double interestEarned(double amount, double noOfDays) {
    	if(noOfDays > 0) {
	    	double interest = 0.0;
	    	double total = 0.0;
	        switch(this.accountType){
	            case SAVINGS:
	                if (amount <= 1000) {
	                    interest = amount * 0.001;
	                }
	                else {
	                	interest =  1 + (amount-1000) * 0.002;
	                }
	                break;
	            case MAXI_SAVINGS:
	                if (amount <= 1000) {
	                	interest =  amount * 0.02;
	                }
	                else if (amount <= 2000) {
	                	interest =  20 + (amount-1000) * 0.05;
	                }
	                else {
	                	interest =  70 + (amount-2000) * 0.1;
	                }
	                break;
	            default:
	            	interest =  amount * 0.001;
	            	break;
	        }
	        interest = (interest/365); //Calculate Daily Accrued Interest
	        total = interest + interestEarned((amount+interest), noOfDays - 1); //Compound Interest
	        return total; 
    	}
    	else {
    		return 0.0;
    	}
    }

    //Sum Transactions to return Balance
    public double sumTransactions() {
    	double amount = 0.0;
        for (Transaction t: transactions) {
            amount += t.getAmount();
        }
        return amount;
    }
    
    //Manually Add a Transaction to Account
    public void addTransaction(Transaction newTransaction) {
    	transactions.add(newTransaction);
    }

}
