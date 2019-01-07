package com.abc;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public abstract class Account implements Interest {
	
    public List<Transaction> transactions;
    
    public Account() {
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            this.transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {  
    	double totalInAccount = checkIfTransactionsExist(true);
    	if (amount <= 0) {
        throw new IllegalArgumentException("amount must be greater than zero");
        
    } else if (totalInAccount < amount) {
    	throw new IllegalArgumentException("amount must be less than what is currently available in the bank");
    	
    } else {
        transactions.add(new Transaction(-amount));
    }
}

    public abstract double interestEarned(); 
    	 
    public double compoundInterest() {
      	DateProvider dateProvider = new DateProvider();
    	Date startDate = transactions.get(0).getTransactionDate();
    	Date endDate = dateProvider.now();
    	double amount = sumTransactions();
    	double originalAmount = sumTransactions();
    	
    	if (startDate.compareTo(endDate) == 0) {
    		return amount; 
    	}
    	   	
    	Calendar startCal = Calendar.getInstance();
    	Calendar endCal = Calendar.getInstance();
    	startCal.setTime(startDate);
    	endCal.setTime(endDate);
    	
    	long daysBetween = ChronoUnit.DAYS.between(startCal.toInstant(), endCal.toInstant());
    	
    	for(int x = 0; x < daysBetween; x++) {
   		amount = amount + (amount * 0.01);
    	}
	
    	return interestRounder(amount - originalAmount); 
    }
    
  
    	public double interestRounder(double amount) {
    		amount = amount * 100;
    		amount = Math.round(amount);
    		return amount / 100;
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
}
