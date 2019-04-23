package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.time.temporal.ChronoUnit;
import java.time.Duration;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

/**
 * 
 * @author fearg
 *
 */
public class Account {

    private final AccountType accountType;
    public List<Transaction> transactions;

    /**
     * constructor with accountType as argument
     * account transactions are set to a new empty ArrayList of type Transactions
     * @param accountType
     */
    public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    /**
     * once the attempted transaction is above 0 then it is added to the arraylist
     * @param amount
     */
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } 
        else {
            transactions.add(new Transaction(amount));
        }
    }

    /**
     * once the attempted transaction is above 0 then it is added to the arraylist
     * there is no check to see whether the customer can afford the transaction
     * @param amount
     */
	public void withdraw(double amount) {
		
	    if (amount <= 0) {
	        throw new IllegalArgumentException("amount must be greater than zero");
	    } 
	    else {
	        transactions.add(new Transaction(-amount));
	    }
	}

	/**
	 * 
	 * @return
	 */
    public double interestEarned() {
    	
        double amount = sumTransactions();
        
        //only calculate interest if the account is in positive balance
        if(amount > 0) {
        	switch(accountType){
            	case SAVINGS:
	                if (amount <= 1000) {
	                    return amount * 0.001;
	                }
	                else {
	                    return (1000 * 0.001) + ((amount-1000) * 0.002);
	                }
	                
	            case MAXI_SAVINGS:
	            	Date mostRecentTransaction;
	            	if(!transactions.isEmpty()) {
	            		mostRecentTransaction = transactions.get(transactions.size() - 1).getTransactionDate();
	            	}
	            	else {
	            		mostRecentTransaction = null;
	            	}
	            	
	            	Date today = new Date();
	            	
	            	LocalDate d1 = mostRecentTransaction.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	            	LocalDate d2 = today.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
	            	Duration diff = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
	            	long diffDays = diff.toDays();
	            	
	            	//if its been more than 10 days since the last transaction then apply 5% rate
	            	if(diffDays > 10) {
	            		return amount * 0.05;
	            	}
	            	else {
	            		return amount * 0.001;
	            	}
	            
	            //default is a checking account
	            default:
	                return amount * 0.001;
	        }
        }
        //otherwise, no interest accrued
        else {
        	return 0;
        }
        
    }

    /**
     * if there are transactions on the account then sum the amounts
     * @return
     */
    public double sumTransactions() {
    	boolean transactionsExist = checkIfTransactionsExist();
    	double amount = 0.0;
    	
    	if(transactionsExist == true) {
    		for (Transaction t: transactions) {
                amount += t.amount;
    		}
    	}
   
        return amount;
    }

    /**
     * if there are transactions on the account then return true
     * @return
     */
    private boolean checkIfTransactionsExist() {
        boolean transactionsExist = false;
        
        if(transactions.isEmpty() == false) {
        	transactionsExist = true;
        }
        
        return transactionsExist;
    }

    /**
     * getter for accountType
     * @return
     */
    public AccountType getAccountType() {
        return accountType;
    }
}
