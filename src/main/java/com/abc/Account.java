package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    private List<Transaction> transactions;
    private Date createdOn;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.createdOn = DateProvider.getInstance().now();
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
    		transactions.add(new Transaction(-amount, Transaction.WITHDRAWAL));
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
            case MAXI_SAVINGS:
                if (accountInactive())
                    return amount * 0.05;
                else
                    return amount * 0.001;
            default:
                return amount * 0.001;
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

    public List<Transaction> getTransactions() {
    	return transactions;
    }
    
    // Check for no withdrawals in last 10 days
    public boolean accountInactive() {
    	Date now = DateProvider.getInstance().now();
    	long a = now.getTime();
    	
    	for(Transaction t : transactions) {
    		if(t.getType() == Transaction.WITHDRAWAL) {
    			long b = t.getDate().getTime();
    			long days = TimeUnit.DAYS.convert(a - b, TimeUnit.MILLISECONDS);
    			if(days < 10)
    				return false;
    		}
    	}
    	
    	return true;
    }
}
