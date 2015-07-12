package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
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
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
            	if (!withdrawalsPastTenDays())
            		return amount * 0.05;
            	else
            		return amount * 0.001;
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
            amount += t.amount;
        return amount;
    }
    
    // Work out if a withdrawal has occurred in the past 10 days
    public boolean withdrawalsPastTenDays() {
    	boolean recentWithdrawals = false;
    	Date today = DateProvider.getInstance().now();
    	for (Transaction t : transactions) {
    		Date transactionDate = t.getTransactionDate();
    		long difference = today.getTime() - transactionDate.getTime();
    		int differenceDays = (int) TimeUnit.MILLISECONDS.toDays(difference);
    		// If in last 10 days and transaction is a withdrawal, set recentWithdrawals to true
    		if (differenceDays <= 10 && t.amount < 0)
    			recentWithdrawals = true;
    	}
    	return recentWithdrawals;
    }

    public int getAccountType() {
        return accountType;
    }

}
