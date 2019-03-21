package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    private List<Transaction> transactions;

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
        double amount = sumTransactions();
        switch(accountType){
            case CHECKING:
            	return amount * 0.001;
            case SAVINGS:
                if (amount <= 1000) {
                    return amount * 0.001;
                }
                else {
                    return 1 + (amount-1000) * 0.002;
                }
            case MAXI_SAVINGS:
            	if (checkIfWithdrew(10, transactions)) {
            		return amount * 0.001;
            	}
            	else {
            		return amount * 0.05;
            	}
            default:
                return amount * 0.001;
        }
    }
    
    /**
     * checksIfWithdrew 
     * checks for withdrawal in the last number of specified days
     */
    // 
    public boolean checkIfWithdrew(int days, List<Transaction> transactions) {
    	boolean didWithdraw = false;
    	int numOfTransactions = transactions.size();
    	if (numOfTransactions == 0) {
    		didWithdraw = false;
    	}
    	else {
    		Date rightNow = DateProvider.getInstance().now();
    		for (int i = (numOfTransactions-1); i < 0; i--) {
    			// checks if money was withdrawn below days threshold
    			if ((DateProvider.daysDiff(rightNow, transactions.get(i).getDate())) < days){
    				if (transactions.get(i).getAmount() < 0) {
    					didWithdraw = true;
    					break;
    				}
    			}
    			else {
    				break;
    			}
    		}
    	}
    	return didWithdraw;
    }
    			
    public double sumTransactions() {
       double amount = 0.0;
       for (Transaction t: transactions) {
           amount += t.getAmount();
       }
       return amount;
    }

    private boolean checkIfTransactionsExist() {
        int numberOfTransactions = transactions.size();
        if (numberOfTransactions > 0) {
            return true;
        }
        else {
            return false;
        }
    }

    public int getAccountType() {
        return accountType;
    }
    
    public List<Transaction> getTransactions(){
    	return transactions;
    }

}
