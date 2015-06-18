package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {
	// enums?
    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;
    private double currentBalance;
    Date previousInterestCheck;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.currentBalance = 0;
        this.previousInterestCheck = DateProvider.getInstance().now();
    }

    public void deposit(double amount, boolean transfer) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
        	// account is 'looked at', therefore enact interest changes
        	Date now = DateProvider.getInstance().now();
        	addInterest(now);
        	
        	if(transfer == false) {
        		transactions.add(new Transaction(amount, Transaction.DEPOSIT));
        	}
        	else {
        		transactions.add(new Transaction(amount, Transaction.TRANSFER_IN));
        	}
            currentBalance += amount;
        }
    }

	public void withdraw(double amount, boolean transfer) {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("amount must be greater than zero");
	    } 
	    else if ((currentBalance - amount) < 0) {
	    	// deny transaction completely for now
	    	throw new IllegalArgumentException(
	    			"requested withdrawal exceeds current account balance");
	    }
	    else {
	    	// account is 'looked at', therefore enact interest changes
	    	Date now = DateProvider.getInstance().now();
	    	addInterest(now);
	    	
	    	if(transfer == false) {
	    		transactions.add(new Transaction(-amount, Transaction.WITHDRAWAL));
	    	}
	    	else {
	    		transactions.add(new Transaction(-amount, Transaction.TRANSFER_OUT));
	    	}
	        currentBalance -= amount;
	    }
	}
	
	public double addInterest(Date currentInterestCheck) {
		// takes the difference between the current date and 
		// previous date in number of days
		long currentTime = currentInterestCheck.getTime();
		long previousTime = previousInterestCheck.getTime();
		long diffTime = currentTime - previousTime;
		if(diffTime < 0) {
			throw new IllegalArgumentException(
					"input date must be later than stored date");
		}
		long diffDay = diffTime / (1000 * 60 * 60 * 24);
		
		int iDay = longToInt(diffDay);
		
		double interest = calculateInterest(iDay);
		
		// update everything
		currentBalance += interest;
		previousInterestCheck.setTime(currentTime);
		
		// count as a transaction?
		transactions.add(new Transaction(interest, Transaction.INTEREST));
		
		// optional return for possible testing
		return currentBalance;
	}
	
	private int longToInt(long input) {
		// this should never happen but in the event that the long
		// to be converted is outside the int range
		if(input < Integer.MIN_VALUE || input > Integer.MAX_VALUE) {
			throw new IllegalArgumentException(
					"input long is outside the int range");
		}
		return (int) input;
	}
	
	private double calculateInterest(int numDays) {
		// get relevant yearly interest rate
		double b = currentBalance;
		double interest = 0.0;
		switch(accountType) {
			case SAVINGS:
				if (b <= 1000) {
					double ir_1 = getInterestRate(numDays, 1.001);
					ir_1 -= 1.0;
					if(ir_1 == 0) {
						// if the interest rate is rounding to zero,
						// set interest to 0
						interest = 0;
					}
					else {
						interest = Math.pow(b, ir_1);
					}
				}
				else {
					double ir_1 = getInterestRate(numDays, 1.001);
					double ir_2 = getInterestRate(numDays, 1.002);
					ir_1 -= 1.0;
					ir_2 -= 1.0;
					b -= 1000;
					if(ir_1 == 0) {
						if(ir_2 == 0) {
							interest = 0;
						}
						else {
							interest = Math.pow(b, ir_2);
						}
					}
					else {
						if(ir_2 == 0) {
							interest = Math.pow(1000, ir_1);
						}
						else {
							interest = Math.pow(1000, ir_1) + Math.pow(b, ir_2);
						}
					}
				}
				return interest;
			case CHECKING:
				double ir = getInterestRate(numDays, 1.001);
				ir -= 1.0;
				interest = Math.pow(b, ir);
				return interest;
			case MAXI_SAVINGS:
				if(numDays >= 10) {
					double ir_3 = getInterestRate(numDays, 1.05);
					ir_3 -= 1.0;
					interest = Math.pow(b, ir_3);
				}
				else {
					double ir_3 = getInterestRate(numDays, 1.001);
					ir_3 -= 1.0;
					interest = Math.pow(b, ir_3);
				}
				return interest;
			default:
				// shouldn't reach here
				throw new IllegalArgumentException("illegal account type");
		}
	}
	
	private double getInterestRate(int numDays, double interest) {
		// returns the modified interest rate from a yearly interest
		// rate (this interest rate should be 1.###)
		
		// numDays should be > 0 since a check is previously made
		
		// unfamiliar with
		// the accuracy of the standard function in java when
		// dealing with powers close to zero
		
		// alternative root finding algorithm is newton's one
		
		double power = numDays / 365.25;
		double rate = Math.pow(interest, power);
		return rate;
	}
	
	// interest earned should be combination of all interest earned since account opening?
	public double interestEarned() {
		double interest = 0.0;
		for (Transaction t : this.transactions) {
			if(t.transactionType == Transaction.INTEREST) {
				interest += t.amount;
			}
		}
		return interest;
	}
	
/*    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
                if (amount <= 1000)
                    return amount * 0.02;
                if (amount <= 2000)
                    return 20 + (amount-1000) * 0.05;
                return 70 + (amount-2000) * 0.1;
            default:
                return amount * 0.001;
        }
    } */

	// this method should be depreciated
    public double sumTransactions() {
       return checkIfTransactionsExist(true);
    }

    // this method should be depreciated
    private double checkIfTransactionsExist(boolean checkAll) {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }
    
    public double getAccountBalance() {
    	return currentBalance;
    }

}
