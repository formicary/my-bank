package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    public static final int DAILY = 0;
    public static final int YEARLY = 1;
    
    private final int accountType;
    public List<Transaction> transactions;
    
    private double accountBalance = 0;	//Add in variable to track current account balance
    private Date dateOfLastWithdrawal;	//Track when the last withdrawal was completed for the Maxi-Savings account
    private boolean hasAtLeastOneWithdrawal = false;	//For Maxi-Savings account, if the user has never withdrawn the dateOfLastWithdrawal variable wouldn't be initialised.

    //Initialise an account, giving an account type
    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    //Put money in the account
    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount));
            accountBalance += amount; //adjust account balance
        }
    }

    //Take money out of the account
	public void withdraw(double amount) {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("amount must be greater than zero");
	    } else if (accountBalance - amount < 0) {
	    	throw new IllegalArgumentException("account balance cannot go below zero");	//Assuming accounts cannot go below a balance of 0
	    } else {
	        transactions.add(new Transaction(-amount));
	        accountBalance -= amount;	//adjust account balance
	        
	        //denote that a withdrawal has taken place on the account
	        dateOfLastWithdrawal = DateProvider.getInstance().now();
	        if (!hasAtLeastOneWithdrawal)
	        	hasAtLeastOneWithdrawal = true;	
	    }
	}
	
	public void accrueAndCompountInterest() {
		accountBalance += interestEarned(DAILY);	//update balance with interest earned daily
	}
	
	//Get amount of interest earned
    public double interestEarned(int accrueCompoundPeriod) {
    	
    	Map<String, Double> rates = new HashMap<String, Double>();	//prefer to use a map for rates as can be easily changes using lambda functions
    	rates.put("basic", 0.001);
    	rates.put("upper", 0.002);
    	rates.put("maxi", 0.05);
    	
    	double savingsBoundary = 1000;
    	
    	if (accrueCompoundPeriod == DAILY) {
    		rates.replaceAll((key, value) -> value / 365);	//Divide by 365 as rates are yearly    		
    	}
    	
        switch(accountType) {
        	case CHECKING:	//Prefer to explicitly define checking type, rather than default
        		return accountBalance * rates.get("basic");
            case SAVINGS:
                if (accountBalance <= savingsBoundary)
                    return accountBalance * rates.get("basic");
                else
                    return (savingsBoundary * rates.get("basic")) + (accountBalance - savingsBoundary) * rates.get("upper");	
            case MAXI_SAVINGS:	//Assumed that the change completely overrode the previous interest scheme
            	Date today = DateProvider.getInstance().now();	
            	int dayBoundary = 10;	//number of days where a transaction resets the interest rate
            	
                //5% if no withdrawals in last 10 days
            	if (!hasAtLeastOneWithdrawal || DateProvider.getInstance().daysBetweenTwoDates(dateOfLastWithdrawal, today) > dayBoundary) { //If never withdrawn (assumed account starts at maxi interest rate) or more than 10 days (assumed inclusive) since the last withdrawal
            		return accountBalance * rates.get("maxi");	//5%
            	} else { //else 0.1%
            		return accountBalance * rates.get("basic");	//0.1%
            	}
            default:
                throw new IllegalArgumentException("account must have an account type to calculate interest");	//Throw an exception if there is no account type set
        }
    }

    public int getAccountType() {
        return accountType;
    }
    
    public double getAccountBalance() {	//Getter for accountBalance as it's private
    	return accountBalance;
    }

}
