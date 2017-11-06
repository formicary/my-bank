package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;

public class Account {
	//accountType options
    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;
    //interest rate options
	private double checkingInterest = 0.001;
	private double lowSavingsInterest = 0.001;
	private double highSavingsInterest = 0.002;
	//private double lowMaxiSavingsInterest = 0.02;
	//private double midMaxiSavingsInterest = 0.05;
	//private double highMaxiSavingsInterest = 0.1;

    //account type variable and list of transactions
    private final int accountType;
    public List<Transaction> transactions;
    
    //setters for interest rates
    public void setCheckingInterestRate(double checkingInterest) { this.checkingInterest = checkingInterest; }
    public void setLowSavingsInterestRate(double lowSavingsInterest) { this.lowSavingsInterest = lowSavingsInterest; }
    public void setHighSavingsInterestRate(double highSavingsInterest) { this.highSavingsInterest = highSavingsInterest; }
    //public void setLowMaxiSavingsInterestRate(double lowMaxiSavingsInterest) { this.lowMaxiSavingsInterest = lowMaxiSavingsInterest; }
    //public void setMidMaxiSavingsInterestRate(double midMaxiSavingsInterest) { this.midMaxiSavingsInterest = midMaxiSavingsInterest; }
    //public void setHighMaxiSavingsInterestRate(double highMaxiSavingsInterest) { this.highMaxiSavingsInterest = highMaxiSavingsInterest; }
    
    //setter for account type
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
    	double interest = 0.0;
        double amount = sumTransactions();
        //Checking Account Interest
        if (accountType == Account.CHECKING) {
        	interest = amount * checkingInterest;
        }
        //Savings Account Interest
        else if (accountType == Account.SAVINGS) {
        	if (amount <= 1000) {
        		interest = amount * lowSavingsInterest;
        	} else {
        		interest = 1 /*(1000 * 0.001)*/ + (amount - 1000) * highSavingsInterest;
        	}
        }
        //Max-Savings Account Interest
        else if (accountType == Account.MAXI_SAVINGS) {
        	//Original interest rate calculations
    		//if (amount <= 1000) {
    			//interest = amount * lowMaxiSavingsInterest;
    		//} else if (amount <= 2000) {
    			//interest = 20 /*1000 * 0.02*/ + (amount - 1000) * midMaxiSavingsInterest;
    		//}
    		//if (amount > 2000) {
    			//interest = 70 /*(1000 * 0.02) + (1000 * 0.05)*/ + (amount - 2000) * highMaxiSavingsInterest;
    		//}
        	
        	//additional feature
        	//interest rate is 5% if the difference between transaction dates is more than 10 days
        	if (isLastTransaction10DaysAfter()) {
        		interest = amount * 0.05;
        	} else {
        		interest = amount * 0.001;
        	}
        }
        return interest;
    }

    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.getAmount();
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }
    
  //compare dates
    public boolean isLastTransaction10DaysAfter() {
    	boolean flag = false;
    	if (transactions.size() >= 2) { //avoid out of bounds exception
    		Calendar lastTransaction = transactions.get(transactions.size()-1).getTransactionDate();
    		Calendar secondLastTransaction = transactions.get(transactions.size()-2).getTransactionDate();
    		
    		//add 10 days to the second to last transaction
    		secondLastTransaction.add(Calendar.DATE, 10);
    		//if the last transaction is now before the second to last transaction 
    		if(lastTransaction.before(secondLastTransaction)) {
    			//then there are less than 10 days between the transactions
    			flag = false;
    		}
    		else {
    			flag = true;
    		}
    	} else {
    		flag = true;
    	}
    	return flag;
    }

}
