package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        	Transaction transaction = new Transaction(amount);
            transactions.add(transaction);
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
        int dayCount;
        switch(accountType){
        case SAVINGS:
        	dayCount = DateUtil.getDayCount(transactions.get(transactions.size()-1).getTransactionDate(), 
    				DateProvider.getInstance().now());
        	if (amount <= 1000)
        		return calcCompundInt(amount, 0.1, dayCount);
            else
                return calcCompundInt(amount-1000, 0.2, dayCount);        
        case MAXI_SAVINGS:
        	dayCount = DateUtil.getDayCount(getLastWithdraw(), DateProvider.getInstance().now());
        	if(dayCount < 10) {
        		return calcCompundInt(amount, 0.1, dayCount);
        	}else {
        		return calcCompundInt(amount, 5.0, dayCount);
        	}
        default:
        	dayCount = DateUtil.getDayCount(transactions.get(transactions.size()-1).getTransactionDate(), 
    				DateProvider.getInstance().now());
        	return calcCompundInt(amount, 5.0, dayCount);
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

    public int getAccountType() {
        return accountType;
    }
    
    public Date getLastWithdraw() {
    	if(transactions.size() == 0) {
    		return null;
    	}
    	Date lastWithdraw = null;  	
    	for(int i=transactions.size()-1; i >= 0; i--) {
    		if(transactions.get(i).amount < 0) {
    			lastWithdraw = transactions.get(i).getTransactionDate();
    		}
    	}
    	if(null == lastWithdraw) {
    		lastWithdraw = transactions.get(transactions.size()-1).getTransactionDate();
    	}
    	return lastWithdraw;
    }
    
    private double calcCompundInt(double amount, double ratePer, int dayCount) {
    	double rate = ratePer/100;
    	double finalAmount = amount * Math.pow(1 + (rate / 365), dayCount);
    	return finalAmount-amount;
    }

}
