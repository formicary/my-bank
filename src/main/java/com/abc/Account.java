package com.abc;

import static java.lang.Math.abs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract class Account {

	public List<Transaction> deposits;
    public List<Transaction> withdrawals;
	private int accountNumber;
	private double balance;
	private Date lastDateInterestCalculated;
    private String statementString;
    private double interestRate;

    public Account(int accountNumber) {
    	this.deposits = new ArrayList<Transaction>();
        this.withdrawals = new ArrayList<Transaction>();
    	this.accountNumber = accountNumber;
    	this.balance = 0;
    	this.lastDateInterestCalculated = DateProvider.getInstance().now();
        this.statementString = "";
        this.interestRate = setInterestRate();
    }
    
    public abstract double interestEarned();
    
    public abstract double setInterestRate();

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            deposits.add(calculateCompoundInterest(new Transaction(amount)));
            balance += amount;
            interestRate = setInterestRate();
            statementString += "  " + "deposit" + " " + HelperClass.toDollars(amount) + "\n";
        }
    }

    public void withdraw(double amount) {
    	if (amount <= 0) {
    		throw new IllegalArgumentException("amount must be greater than zero");
    	}
    	if (balance - amount < 0) {
    		throw new IllegalArgumentException("insufficent funds");
    	} else {
    		withdrawals.add(calculateCompoundInterest(new Transaction(-amount)));
    		balance -= amount;
    		interestRate = setInterestRate();
    		statementString += "  " + "withdrawal" + " " + HelperClass.toDollars(amount) + "\n";
    	}
    }
    
    public int getAccountNumber() {
    	return accountNumber;
    }
    
    public double getInterestRate() {
    	return interestRate;
    }
    
    public double getBalance() {
    	return balance;
    }
    
    public double updateBalance() {
    	calculateCompoundInterest(new Transaction(0));
    	return balance;
    }
    
    private Transaction calculateCompoundInterest(Transaction transaction) {
    	long numberOfDays = HelperClass.calculateDateDifference(lastDateInterestCalculated, transaction.getTransactionDate());
    	for (int i = 0; i < numberOfDays; i++) {
    		balance *= (1 + getInterestRate()/365); //change to days in the specific year  (leap year) ?
    		interestRate = setInterestRate();
    	}
    	lastDateInterestCalculated = transaction.getTransactionDate();
    	return transaction;
    }
    
    @Override
    public String toString() {
    	
    	return statementString + "Total " + HelperClass.toDollars(balance);
    }

}
