package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount, Transaction.DEPOSIT));
        }
    }

	public void withdraw(double amount) {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("amount must be greater than zero");
	    } else {
	        transactions.add(new Transaction(-amount, Transaction.WITHDRAW));
	    }
	}
	
	public void transfer(double amount, Account account) {
		// Money goes from the account the transfer method is called on
		// and moved to the account specified in the method parameters.
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			account.transactions.add(new Transaction(amount, this));
			transactions.add(new Transaction(-amount, account));
		}
	}

    public double interestEarned() {
       double interestEarned = 0.0;
       double interestRate; 
        
       Iterator<Transaction> transactionsIterator = transactions.iterator();
       Transaction firstTransaction = transactionsIterator.next();
        
       double amount = firstTransaction.getAmount();
       double totalAccrued = amount;
       Date prevDate = firstTransaction.getTransactionDate();
       Date secondDate;
       Date now = DateProvider.getInstance().now();
       long daysDiff;
        
       switch (accountType) {
       	   case SAVINGS:
       		   while (transactionsIterator.hasNext()) {
       			   Transaction nextTransaction = transactionsIterator.next();
       			   totalAccrued = amount;
       			   
       			   secondDate = nextTransaction.getTransactionDate();
       			   daysDiff = DateProvider.differenceBetweenDays(secondDate, prevDate);
       			     
       			   while (daysDiff != 0) {
       				   if (totalAccrued <= 1000)
       					   interestRate = 0.001;
       				   else
       					   interestRate = 0.002;
       				   
       				   totalAccrued *= (1 + interestRate / 365);

       				   daysDiff--;
       			   }
       			   
       			   interestEarned = totalAccrued - amount;
       			   amount = totalAccrued + nextTransaction.getAmount();
       			   prevDate = secondDate;
       		   }
       		   
	     	   daysDiff = DateProvider.differenceBetweenDays(now, prevDate);
	     	   
	     	   while (daysDiff != 0) {
  				   if (totalAccrued <= 1000)
  					   interestRate = 0.001;
  				   else
  					   interestRate = 0.002;
  				   
  				   totalAccrued *= (1 + interestRate / 365);

  				   daysDiff--;
  			   }
	     	   
	     	   interestEarned += totalAccrued - amount;

	     	   return interestEarned;
       	   case MAXI_SAVINGS:
       		   int daysSinceWithdrawal = 11;
       		   int transactionType = firstTransaction.getTransactionType();
       		   if (transactionType == Transaction.WITHDRAW)
       			   daysSinceWithdrawal = 0;
   
       		   while (transactionsIterator.hasNext()) {
       			   Transaction nextTransaction = transactionsIterator.next();
       			   totalAccrued = amount;
       			   
       			   if (transactionType == Transaction.WITHDRAW)
        			   daysSinceWithdrawal = 0;
       			   
       			   
       			   
       			   secondDate = nextTransaction.getTransactionDate();
       			   daysDiff = DateProvider.differenceBetweenDays(secondDate, prevDate);
       			   
       			   while (daysDiff != 0) {
       				   if (daysSinceWithdrawal <= 10)
       					   interestRate = 0.001;
       				   else
       					   interestRate = 0.05;
       				   
       				   totalAccrued *= (1 + interestRate / 365);
       				   
       				   daysDiff--;
       				   daysSinceWithdrawal++;    				  
       			   }
       			   
       			   interestEarned += totalAccrued - amount;
    			   amount = totalAccrued + nextTransaction.getAmount();
    			   prevDate = secondDate;
    			   transactionType = nextTransaction.getTransactionType();
       		   }
       		   
			   if (transactionType == Transaction.WITHDRAW)
	 			   daysSinceWithdrawal = 0;	   
			
			   daysDiff = DateProvider.differenceBetweenDays(now, prevDate);
			   totalAccrued = amount;
					   
			   while (daysDiff != 0) {
				   if (daysSinceWithdrawal <= 10)
					   interestRate = 0.001;
				   else
					   interestRate = 0.05;
				   
				   totalAccrued *= (1 + interestRate / 365);
				   
				   daysDiff--;
				   daysSinceWithdrawal++;
			   }
			   
			   interestEarned += totalAccrued - amount;
			   
			   return interestEarned;
		   default:
			   while (transactionsIterator.hasNext()) {
       			   Transaction nextTransaction = transactionsIterator.next();
       			   totalAccrued = amount;
       			   
       			   secondDate = nextTransaction.getTransactionDate();
       			   daysDiff = DateProvider.differenceBetweenDays(secondDate, prevDate);

       			   totalAccrued *= Math.pow((1 + 0.001 / 365), daysDiff); 
       			   
	       		   interestEarned = totalAccrued - amount;
	       		   amount = totalAccrued + nextTransaction.getAmount();
	       		   prevDate = secondDate;
       		   }
       		 	  
       		   daysDiff = DateProvider.differenceBetweenDays(now, prevDate);
	     	   
       		   totalAccrued = amount * Math.pow((1 + 0.001 / 365), daysDiff); 
       		   
	     	   interestEarned += totalAccrued - amount;
	     	   
	     	   return interestEarned;
       }
    }

    public double sumTransactions() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.getAmount();
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }
    
    public String toString(int accountType) {
    	switch(accountType) {
    		case SAVINGS:
    			return "Savings Account";
    		case MAXI_SAVINGS:
    			return "Maxi Savings Account";
    		default:
    			return "Checking Account";			
    	}
    }

}
