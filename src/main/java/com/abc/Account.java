package com.abc;


import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.time.temporal.ChronoUnit;

public class Account {
	//accountType options
    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;
    //interest rate options
	private double checkingInterest = 0.001;
	private double lowSavingsInterest = 0.001;
	private double highSavingsInterest = 0.002;
	private double lowMaxiSavingsInterest = 0.001;
	//private double midMaxiSavingsInterest = 0.05;
	private double highMaxiSavingsInterest = 0.05;

    //account type variable and list of transactions
    private final int accountType;
    public List<Transaction> transactions;
    
    //setters for interest rates
    public void setCheckingInterestRate(double checkingInterest) { this.checkingInterest = checkingInterest; }
    public void setLowSavingsInterestRate(double lowSavingsInterest) { this.lowSavingsInterest = lowSavingsInterest; }
    public void setHighSavingsInterestRate(double highSavingsInterest) { this.highSavingsInterest = highSavingsInterest; }
    public void setLowMaxiSavingsInterestRate(double lowMaxiSavingsInterest) { this.lowMaxiSavingsInterest = lowMaxiSavingsInterest; }
    //public void setMidMaxiSavingsInterestRate(double midMaxiSavingsInterest) { this.midMaxiSavingsInterest = midMaxiSavingsInterest; }
    public void setHighMaxiSavingsInterestRate(double highMaxiSavingsInterest) { this.highMaxiSavingsInterest = highMaxiSavingsInterest; }
    
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
    	double newAmount = 0;
    	double interest = 0;
        double amount = sumTransactions();
        Calendar firstTransactionDate = transactions.get(0).getTransactionDate();
        Calendar lastTransactionDate = transactions.get(transactions.size()-1).getTransactionDate();
        
        //Checking Account Interest
        if (accountType == Account.CHECKING) {
        	newAmount = amount * dailyCompoundInterest(checkingInterest,daysBetween(firstTransactionDate,lastTransactionDate));
        }
        //Savings Account Interest
        else if (accountType == Account.SAVINGS) {
        	if (amount <= 1000) {
        		newAmount = amount * dailyCompoundInterest(lowSavingsInterest, daysBetween(firstTransactionDate,lastTransactionDate));
        	} else {
        		newAmount = (1000 * dailyCompoundInterest(lowSavingsInterest, daysBetween(firstTransactionDate,lastTransactionDate)) + (amount - 1000) * dailyCompoundInterest(highSavingsInterest,daysBetween(firstTransactionDate,lastTransactionDate)));
        	}
        }
        //Max-Savings Account Interest
        else if (accountType == Account.MAXI_SAVINGS) {
        	//additional feature
        	//interest rate is 5% if the difference between WITHDRAWAL dates is more than 10 days
        	if (isLastWithdrawal10DaysAfter()) {
        		newAmount = amount * dailyCompoundInterest(highMaxiSavingsInterest,daysBetween(firstTransactionDate,lastTransactionDate));
        	} else {
        		newAmount = amount * dailyCompoundInterest(lowMaxiSavingsInterest,daysBetween(firstTransactionDate,lastTransactionDate));
        	}
        	
        	//Original interest rate calculations
    		//if (amount <= 1000) {
    			//interest = amount * lowMaxiSavingsInterest;
    		//} else if (amount <= 2000) {
    			//interest = 20 /*1000 * 0.02*/ + (amount - 1000) * midMaxiSavingsInterest;
    		//}
    		//if (amount > 2000) {
    			//interest = 70 /*(1000 * 0.02) + (1000 * 0.05)*/ + (amount - 2000) * highMaxiSavingsInterest;
    		//}
       	
        }
        interest = newAmount - amount;
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
    
    public boolean isLastWithdrawal10DaysAfter() {
    	boolean flag = true;
    	if (transactions.size() >= 2) { //avoid out of bounds exception
    		Transaction lastTransaction = transactions.get(transactions.size()-1);
    		Transaction secondLastTransaction = transactions.get(transactions.size()-2);
    		Calendar lastTransactionDate = lastTransaction.getTransactionDate();
    		Calendar secondLastTransactionDate = secondLastTransaction.getTransactionDate();
    		
    		//check if the lastTransaction was a withdrawal
    		if (lastTransaction.getAmount() < 0) {
    			//add 10 days to the second to last transaction
    			secondLastTransactionDate.add(Calendar.DATE, 10);
    			//if the last transaction is now before the second to last transaction 
    			if(lastTransactionDate.before(secondLastTransactionDate)) {
    				//then there are less than 10 days between the transactions
    				flag = false;
    			}
    		}

    	}
    	return flag;
    }
    
    public double daysBetween(Calendar startDate, Calendar endDate) {
    	return ChronoUnit.DAYS.between(startDate.toInstant(), endDate.toInstant());
    }
    
    public double dailyCompoundInterest(double interestRate, double days) {
    	double dailyInterestRate = Math.pow(1+(interestRate/365), days);
    	if (days < 2) { //avoids weird power of 0 calculations
    		dailyInterestRate = 1+(interestRate/365);
    	}
    	return dailyInterestRate;
    }
    
}
