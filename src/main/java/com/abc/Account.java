package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Account implements CustomerAccount {

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
        switch(accountType) {
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
// TODO
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
            	if(haveWithdawalsBeenMade())
            		return amount * 0.001;
            	else
            		return amount * 0.05;
            default:
                return amount * 0.001;
        }
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
    
    private boolean haveWithdawalsBeenMade() {   	
    	long DAY_IN_MS = 1000 * 60 * 60 * 24;
    	double totalSoFar = 0.0;
    	double today = 0.0;
    	double yesterday = 0.0;
    	int DAYS = 10;
    	boolean withdrawal = false;
    	
    	for (int i = DAYS-1; i > 0; i--) {
    		Date d = new Date(System.currentTimeMillis() - (i * DAY_IN_MS));
    		
    		for (Transaction t : transactions) {
    			if (t.getTransactionDate().toString().equals(d.toString()))
    				today += t.getAmount();
    		}
    		
    		totalSoFar += today;
    		
    		if (totalSoFar > yesterday) {
    			yesterday = totalSoFar;
    			today = 0.0;
    		} else {
    			withdrawal = true;
    			break;
    		}
    	}
   	
    	return withdrawal;
    }

    public int getAccountType() {
        return accountType;
    }
    
    public List<Transaction> getTransactions() {
    	return transactions;
    }

}
