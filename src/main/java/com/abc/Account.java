package com.abc;

import static java.lang.Math.abs;

import java.util.ArrayList;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import consts.Constants;

public class Account {

    private final long id;
    private final int accountType;
    private List<Transaction> transactions = new ArrayList<Transaction>();
    private final Date creationDate = new Date();
    private double interestEarned = 0;
    private final Locale locale;		// Used for the currency
    private static Integer counter = 0;	// Used to assign a unique ID to accounts
   
    /**
     * @param accountType
     * @param locale : (an example is Locale.UK)
     * @throws IllegalArgumentException
     */
    public Account(int accountType, Locale locale) throws IllegalArgumentException {
    	// This make sure that only valid objects are created
    	if (accountType != Constants.ACCOUNT_CHECKING_ID &&
    			accountType != Constants.ACCOUNT_SAVINGS_ID && 
    			accountType != Constants.ACCOUNT_MAXI_SAVINGS_ID) {
    		throw new IllegalArgumentException("accountType Not Allowed!");
    	} else {
    		this.accountType = accountType;
    	}
    	this.locale = locale;
        this.id = ++counter;
    }
    
    public long getId() {
		return id;
	}
    public int getAccountType() {
        return accountType;
    }
    public List<Transaction> getTransactions() {
		return transactions;
	}
    public double getInterestEarned() {
    	return interestEarned;
    }
    public String getCurrencySymbol(){
    	return Currency.getInstance(locale).getSymbol(locale);
    }
	
    // Utility Methods

    public void deposit(double amount) throws IllegalArgumentException {
        if (amount <= 0) {
            throw new IllegalArgumentException(AMOUNT_ERROR_MEX);
        } else {
            transactions.add(new Transaction(amount, Constants.DEPOSIT));
        }
    }
    
	public void withdraw(double amount) throws IllegalArgumentException, UnsupportedOperationException  {
		// I want to assume that for this banking app the account amounts can't be less than 0.
		if (amount > getTotalAmount()) {
			throw new UnsupportedOperationException(
					"The withdrawal amount is greater than total account amount.");
		}
	    if (amount <= 0) {
	        throw new IllegalArgumentException(AMOUNT_ERROR_MEX);
	    } else {
	        transactions.add(new Transaction(-amount, Constants.WITHDRAWAL));
	    }
	}
	
	// It accrues one-day interest rates. Ideally it should be called once a day.
    public void accrueInterests() {
        double amount = getTotalAmount();
        switch(accountType){
	        case Constants.ACCOUNT_CHECKING_ID:
	        	interestEarned += calculateDailyInterest(amount, Constants.ZERO_ONE_PERCENT);
	        	break;
	        case Constants.ACCOUNT_SAVINGS_ID:
	            if (amount <= 1000)
	            	interestEarned += calculateDailyInterest(amount, Constants.ZERO_ONE_PERCENT);
	            else
	            	interestEarned += ( ((double) 1/365) + calculateDailyInterest((amount-1000), Constants.ZERO_TWO_PERCENT) );
	            break;
	        case Constants.ACCOUNT_MAXI_SAVINGS_ID:
	        	// Changed Maxi-Savings accounts to have an interest rate of:
	        	// - 5% assuming no withdrawals in the past 10 days 
	        	// - otherwise 0.1%
	        	Transaction lastWithdrawal = getLastWithdrawal();
	        	if( lastWithdrawal==null || lastWithdrawal.isOlder10Days() ) {
	        		interestEarned += calculateDailyInterest(amount, Constants.FIVE_PERCENT);
	        	} else {
	        		interestEarned += calculateDailyInterest(amount, Constants.ZERO_ONE_PERCENT);
	        	}
	        	break;
	    }
    }
    
    public double calculateDailyInterest(double amount, double percentage) {
    	return (amount * percentage) / 365;
    }
    
    public double getTotalAmount() {
    	double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.getAmount();
        return amount;
    }
    
    public Transaction getLastWithdrawal() {
    	int size = transactions.size();
    	for (int i=size-1; i>0; i--) {
    		if ( transactions.get(i).getTransactionType().equals(Constants.WITHDRAWAL) ) {
    			return transactions.get(i);
    		}
    	}
    	return null;
    }
    
    // Get Statement Account with all transactions
    public String getStatement() {
    	// Get account name
    	String s = "\n" + getAccountTypeName() + "\n";
        // Transactions
        for (Transaction t : transactions) {
        	s += "\t" + t.getTransactionType() + " " + formatAmount(t.getAmount()) + "\n";
        }
        s += "Total: " + formatAmount(getTotalAmount()) + "\n";
        return s;
    }
    
    public String getAccountInformation() {
    	// Get account 
    	String s = "\n" + getAccountTypeName() + "\n";
        s += "ID: " + id + "\n";
        s += "Currency: " + getCurrencySymbol() + "\n";
        s += "Open on: " + creationDate + "\n";
        s += "Total: " + formatAmount(getTotalAmount());
        return s;
    }
    
    // Format amount including the right currency
    private String formatAmount(double amount) {
    	String formattedStr = getCurrencySymbol();
    	formattedStr += String.format("%,.2f", abs(amount));
    	return formattedStr;
    }
    
    public String getAccountTypeName() {
        switch(accountType){
            case Constants.ACCOUNT_CHECKING_ID:
                return Constants.ACCOUNT_CHECKING_NAME;
            case Constants.ACCOUNT_SAVINGS_ID:
            	return Constants.ACCOUNT_SAVINGS_NAME;
            case Constants.ACCOUNT_MAXI_SAVINGS_ID:
            	return Constants.ACCOUNT_MAXI_SAVINGS_NAME;
            default:
            	return "Account type not recognized";
        }
    }
    
    private static final String AMOUNT_ERROR_MEX = "Amount must be greater than zero.";
    
}
