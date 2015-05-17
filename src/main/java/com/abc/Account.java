package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/***
 * This class holds a single account. The account class is not directly editable.
 * All interactions with the account class are handled through the Customer class.
 * @author Donald Campbell (campbell.donald@gmail.com)
 *
 */
public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    
    private Date openingDate;
    
    private List<Transaction> transactions;

    /**
     * Constructor accepting an account type. Assumes current system date for opening date.
     * @param accountType The type of account (checking, savings, maxi_savings).
     */
    public Account(int accountType) {
        this(accountType, DateProvider.getInstance().now());
    }
    
    /**
     * Constructor accepting a specified opening date.
     * @param accountType The type of account (checking, savings, maxi_savings)
     * @param openingDate A date object representing the opening date.
     */
    public Account(int accountType, Date openingDate) {
    	this.accountType = accountType;
    	this.transactions = new ArrayList<Transaction>();
    	
    	this.openingDate = openingDate;
    }
    
    /**
     * Returns the number of transactions currently posted to this account.
     * @return The number of transactions currently posted to this account.
     */
    public int getNumberOfTransactions() {
    	return transactions.size();
    }
    
    /**
     * Returns a given transaction, referenced by index. Returns null if index is outside of range of transactions.
     * @param index The index of transaction caller wishes to have returned.
     * @return The transaction object representing the transaction at the given index.
     */
    public Transaction getTransaction(int index) {
    	if (index >= 0 && index < transactions.size()) {
    		return transactions.get(index);
    	} else {
    		return null;
    	}
    }
    
    /** 
     * Posts a new transaction to the account.
     * @param toAdd The transaction to add.
     */
    public void addTransaction(Transaction toAdd) {
    	transactions.add(toAdd);
    }

    /**
     * Calculate interest on the account, as of a specific date.
     * @param asOf The date to use as a reference point for calculating the interest.
     * @return Interest earned on the account, as of a certain date.
     */
    public double calculateInterest(Date asOf) {
        double amount = 0.0, interest, totalInterest = 0.0;
        Date transactionDate, lastPosted = openingDate, lastWithdrawalDate = null;
        Transaction transaction;
        
        for (int index = 0; index < transactions.size(); index++) {
        	transaction = transactions.get(index);
        	transactionDate = transaction.getTransactionDate();
        	 
        	if (transactionDate.after(asOf)) {
        		break;
        	}
        	
        	interest = compoundInterest(amount, lastWithdrawalDate, transactionDate, lastPosted);
        	
        	if (transaction.getAmount() < 0) {
        		lastWithdrawalDate = transactionDate;
         	} 
        	
        	amount += transaction.getAmount();
        	amount += interest;
        	totalInterest += interest;
        	
        	lastPosted = transactionDate;
        } 
        
        interest = compoundInterest(amount, lastWithdrawalDate, asOf, lastPosted);	
        totalInterest += interest; 
        
        return Math.round(totalInterest * 100.0) / 100.0;
    }
    
    /**
     * Calculate interest on the account as of the current system date.
     * @return The interest earned by the account, as of the current system date.
     */
    public double calculateInterest() {
    	return calculateInterest(DateProvider.getInstance().now());
    }
    
    /**
     * Calculate the interest earned (compounded daily) between the last posted transaction date and a reference date given an opening balance, the date of the last withdrawal, and the date of the last posted transaction.
     * @param startAmount The starting amount of the account.
     * @param lastWithdrawalDate The date of the last withdrawal transaction.
     * @param referenceDate The reference date to compound your interest to.
     * @param lastPosted The date of the last posted transaction.
     * @return The total amount of interest accrued between the date of the last posted transaction and the reference date.
     */
    private double compoundInterest (double startAmount, Date lastWithdrawalDate, Date referenceDate, Date lastPosted) {
    	double amount = startAmount, totalInterest = 0.0, interest, daysSinceLastWithdrawal;
    	int numberOfDays = (int) Math.floor(HelperFunctions.daysBetween(referenceDate, lastPosted));
    	boolean withdrawalInRange;
    	
        for (int x = 0; x < numberOfDays; x++) {
        	if (lastWithdrawalDate != null) {
        		daysSinceLastWithdrawal = Math.floor(HelperFunctions.daysBetween(lastWithdrawalDate, lastPosted));
        	
        		if ((daysSinceLastWithdrawal + x) > 10.0) {
        			withdrawalInRange = false;
        		} else {
        			withdrawalInRange = true;
        		}
        	} else {
        		withdrawalInRange = false;
        	} // else
    		
    		interest = calculateInterestOnAmount(amount, withdrawalInRange);
    			
    		totalInterest += interest;
        	amount += interest;
    	} 
        
        return totalInterest;
    }
    
    /**
     * Calculate the amount of interest compounded in one day given an account balance amount, and whether a withdrawal operation was posted within the last 10 days.
     * @param amount The account balance off which to calculate the daily-compounded interest
     * @param withdrawalInRange A boolean representing whether a withdrawal transaction was posted within the last 10 days.
     * @return The amount of interest earned in one day on the given amount.
     */
    private double calculateInterestOnAmount(double amount, boolean withdrawalInRange) {
    	double interest;
    	
    	switch(accountType) {
	    	case CHECKING:
	    		interest = amount * (0.001 / 365.0);
	    		break;
	    		
	        case SAVINGS:
	            if (amount <= 1000) {
	                interest = amount * (0.001 / 365.0);
	            } else {
	                interest = (1000 * (0.002 / 365.0)) + ((amount - 1000) * (0.002 / 365.0));
	            } // else
	            break;
	            
	        case MAXI_SAVINGS:
	        	if (!withdrawalInRange) {
	        		interest = amount * (0.05 / 365.0);
	        	} else {
	        		interest = amount * (0.001 / 365.0);
	        	} // else
	        	
	        	break;
	            
	        default:
	            interest = amount * 0.001;
	            break;
	    }
    	
    	return interest;
    }

    /**
     * Returns a sum of all the transactions posted to the account (not taking account of interest).
     * @return The sum of all transactions posted to the account (not taking into account interest).
     */
    public double sumTransactions() {
    	return sumTransactions (DateProvider.getInstance().now());
    }
    
    /**
     * Returns a sum of all the transactions posted to the account (not taking account of interest), as of a given date.
     * @param asOf Date to use when calculating the sum of transactions.
     * @return The sum of transactions as of the given date.
     */
    public double sumTransactions (Date asOf) {
    	double totalAmount = 0.0;
    	
    	for (Transaction transaction : transactions) {
    		if (transaction.getTransactionDate().after(asOf)) {
    			break;
    		} // if
    		
    		totalAmount += transaction.getAmount();
    	}  
    	
    	return totalAmount;
    } 
    
    /**
     * Returns the current balance of the account, as of the current system date.
     * @return The current balance of the account.
     */
    public double getBalance() {
    	return sumTransactions(DateProvider.getInstance().now()) + calculateInterest(DateProvider.getInstance().now());
    } 
    
    /** 
     * Returns the current balance of the account, as of a given date.
     * @param asOf Date to use when quoting current account balance.
     * @return Account balance as of the given date.
     */
    public double getBalance(Date asOf) {
    	return sumTransactions(asOf) + calculateInterest(asOf);
    }

    /**
     * Getter method for the account type.
     * @return The account type.
     */
    public int getAccountType() {
        return accountType;
    }
}
