package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

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
        double totalAccrued = 0.0;
        
        Iterator<Transaction> transactionsIterator = transactions.iterator();
        Transaction firstTransaction = transactionsIterator.next();
        
        double amount = firstTransaction.getAmount();
        Date prevDate = firstTransaction.getTransactionDate();
        Date secondDate;
        Date now = DateProvider.getInstance().now();
        long daysDiff;
        
        /**
        while (transactionsIterator.hasNext()) {
        	Transaction nextTransaction = transactionsIterator.next();
        	
        	switch (accountType) {
	            case SAVINGS:
	            	double interestRate;
	                if (amount <= 1000.0) {
	                	interestRate = 0.001;
	                	return amount * 0.001;
	                } else {
	                	interestRate = 0.002;
	                	return amount * 0.002;
	                }
	                	
	                
	                secondDate = nextTransaction.getTransactionDate();
	                daysDiff = DateProvider.differenceBetweenDays(firstDate, secondDate);
	                
	                totalAccrued = amount * Math.pow(1 + interestRate / 365, daysDiff / 365);
	                interestEarned += totalAccrued - amount;
	                amount = totalAccrued;
	                
        
	            case MAXI_SAVINGS:
	                for (Transaction t: transactions) {
	                	if (t.getTransactionType() == Transaction.WITHDRAW) {
	                		Date now = DateProvider.getInstance().now();
	                		Date transactionDate = t.getTransactionDate();                
	                		
	                		long msDiff = Math.abs(now.getTime() - transactionDate.getTime());
	                		daysDiff = TimeUnit.DAYS.convert(msDiff, TimeUnit.MILLISECONDS);
	
	                		if (daysDiff <= 10)
	                			return amount * 0.001;
	                	}
	                }
	                return amount * 0.05;
	            default:
	            	secondDate = nextTransaction.getTransactionDate();
	                daysDiff = DateProvider.differenceBetweenDays(secondDate, prevDate);
	                System.out.println(daysDiff);
	                totalAccrued = (amount * Math.pow(1 + 0.001 / 365, daysDiff));
	                System.out.println("Accrued " + totalAccrued);
	                interestEarned += totalAccrued - amount;
	                amount = totalAccrued;

        	}
       } */
        
       switch (accountType) {
       	   case CHECKING:
       		   
       		   while (transactionsIterator.hasNext()) {
       			   Transaction nextTransaction = transactionsIterator.next();
       			   totalAccrued = amount;
       			   
       			   secondDate = nextTransaction.getTransactionDate();
       			   daysDiff = DateProvider.differenceBetweenDays(secondDate, prevDate);

	       			/** while (daysDiff != 0) {
    				   double interestRate = 0.001;
    				   totalAccrued *= (1 + interestRate);
    				   
    				   daysDiff--;
	    			} */
       			   totalAccrued = totalAccrued * Math.pow((1 + 0.001), daysDiff); 
       			   
	       		   interestEarned = totalAccrued - amount;
	       		   amount = totalAccrued + nextTransaction.getAmount();
	       		   prevDate = secondDate;
       		   }
       		 	  
       		   daysDiff = DateProvider.differenceBetweenDays(now, prevDate);
	     	   
       		   totalAccrued = amount * Math.pow((1 + 0.001), daysDiff); 
       		   
	     	   interestEarned += totalAccrued - amount;
	     	   
	     	   return interestEarned;
       	   case SAVINGS:
       		   double interestRate;
       		   while (transactionsIterator.hasNext() ) {
       			   Transaction nextTransaction = transactionsIterator.next();
       			   
       			   secondDate = nextTransaction.getTransactionDate();
       			   daysDiff = DateProvider.differenceBetweenDays(secondDate, prevDate);
       			   
       			   
       			   while (daysDiff != 0) {
       				   if (amount <= 1000)
       					   interestRate = 0.001;
       				   else
       					   interestRate = 0.002;
       				   
       				   totalAccrued = amount * (1 + interestRate / 365);
       				   interestEarned += totalAccrued - amount;
       				   amount += interestEarned;
       				   
       				   daysDiff--;
       			   }
       			   
       			   amount += nextTransaction.getAmount();
       			   prevDate = secondDate;
       		   }
       		   
	     	   daysDiff = DateProvider.differenceBetweenDays(now, prevDate);
	     	   
	     	   while (daysDiff != 0) {
	     		   if (amount <= 1000)
	     			   interestRate = 0.001;
	     		   else
	     			   interestRate = 0.002;
	     		   
	     		   totalAccrued = amount * (1 + interestRate / 365);
  				   interestEarned += totalAccrued - amount;
  				   amount += interestEarned;
  				   
  				   daysDiff--;
	     	   }
	     	   System.out.println("Accrued " + totalAccrued + " Amount " + amount);
	     	   System.out.println("interest " + interestEarned);
	     	   return interestEarned;
       	   default:
       		   return 0;
       		   
       		   
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
