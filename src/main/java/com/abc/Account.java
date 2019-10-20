package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Account {
	// account types
    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;
    // constants
    public static final double basicInterest = 0.1;
    public static final double savingsThreshold = 1000;
    public static final double maxBasicInterest = 0.0027378507871844704;
    public static final double savingsInterest = 0.2;
    public static final double maxiInterest = 5;
    public static final long maxiLapse = 10;
    // variables belonging to the Account
    private Calendar lastUpdate;
    private Calendar lastWithdrawal;
    private double subtotal;
    private double interestPaid; 
    private final int accountType;
    public List<Transaction> transactions;

    public Account(int accountType) {
    	if (isValidAccountType(accountType)) {
    		this.accountType = accountType;
    		transactions = new ArrayList<Transaction>();
    		subtotal = 0.0;
    		interestPaid = 0.0;
    		lastUpdate = DateProvider.getInstance().now();
    	} else {
    		throw new IllegalArgumentException("Invalid account type");
    	}        
    }
    
    public static boolean isValidAccountType(int accountType) {
    	if (accountType == CHECKING || accountType == SAVINGS || accountType == MAXI_SAVINGS) {
    		return true;
    	} else {
    		return false;
    	}	
    }

    public void deposit(double amount) {
        if (amount <= 0) {throw new IllegalArgumentException("amount must be greater or equal to zero");}
        compounding();
        transactions.add(new Transaction(amount));
        subtotal += amount;
    }

	public void withdraw(double amount) {
	    if (amount <= 0) {throw new IllegalArgumentException("amount must be greater than zero");}
	    compounding();
	    if (amount > getBalance()) {throw new IllegalArgumentException("insufficient funds on account");}
	    transactions.add(new Transaction(-amount));
	    subtotal += -amount;
	    lastWithdrawal = DateProvider.getInstance().now();
	}
	
	public void transfer(double amount, Account recipient) {
		withdraw(amount);
		recipient.deposit(amount);
	}
	
	// calculates daily compound interests accrued since last compounding
	public void compounding() {
		if (transactions.size() == 0) {return;}
		
		Calendar current = DateProvider.getInstance().now();
		long sinceUpdate = DateProvider.daysBetween(lastUpdate, current);
		long updateToWithdrawal = -1;	// default value to signify no last withdrawal
		if (lastWithdrawal != null) {
			long sinceWithdrawal = DateProvider.daysBetween(lastWithdrawal, current);
			// how many days between last update and last withdrawal
			updateToWithdrawal = sinceWithdrawal - sinceUpdate; 
		}

		if (sinceUpdate > 0) {
			switch(this.accountType) {
				case SAVINGS:
					// sub $1000 interest regime
					if (subtotal <= savingsThreshold) {
						interestPaid += dailyCompound(getBalance(), basicInterest, sinceUpdate);
					// interest calculation above $1000
					} else {
						interestPaid += dailyCompound(getBalance() - savingsThreshold,
								savingsInterest, sinceUpdate, maxBasicInterest);
					}
					break;
				case MAXI_SAVINGS:
					long remainingDaysOnBasic = 0;
					// calculates interest accrued with reduced interest rate (recent withdrawal)
					if (updateToWithdrawal >= 0 && updateToWithdrawal <= maxiLapse) {
						remainingDaysOnBasic = maxiLapse - updateToWithdrawal;
						interestPaid += dailyCompound(getBalance(), basicInterest, remainingDaysOnBasic);
					}
					// calculates interest accrued at normal rate (more than 10 days after withdrawal)
					interestPaid += dailyCompound(getBalance(), maxiInterest, sinceUpdate - remainingDaysOnBasic);
					break;
				// interest rate for checking account
				default:
					interestPaid = dailyCompound(getBalance(), basicInterest, sinceUpdate);
					break;
			}
		}
		lastUpdate = current;
	}	
	
	// compound interest calculation
	// implementing: (1 + interest)^n * amount - amount
	private double dailyCompound(double amount, double interestPerAnnum, long daysSinceLast) {
		double divider = 365.25;
		double coefficient = 1 + ((interestPerAnnum/100) /divider);
		return amount*Math.pow(coefficient, daysSinceLast) - amount;
	}
	
	// more advanced compound interest calculation
	// implementing: (1 + interest)^n * amount - amount + increment * sum{n=0 to n-1}((1 + interest/100)^n);
	// this accounts for the interest on the interest for the savings account: the first $1000
	// are subject to a special interest regime. So, the interest produced by that regime is then 
	// subject to the same interest rate as every other dollar after $1000 in the account. 
	private double dailyCompound(double amount, double interestPerAnnum, long daysSinceLast, double dailyIncrement) {
		double divider = 365.25;
		double coefficient = 1 + ((interestPerAnnum/100) /divider);
		double mainInterest = amount*Math.pow(coefficient, daysSinceLast) - amount;
		double subsidiaryCoefficient = 0;
		for (int i = 0; i < daysSinceLast; i++) {
			subsidiaryCoefficient += Math.pow(coefficient, i);
		}
		return  mainInterest + dailyIncrement * subsidiaryCoefficient;
	}
    
    public double getBalance() {
    	return subtotal + interestPaid;
    }
    
    public double getInterestsPaid() {
    	return interestPaid;
    }

    public int getAccountType() {
        return accountType;
    }

}
