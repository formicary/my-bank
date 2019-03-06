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
            transactions.add(new Transaction(amount));
        }
    }

	public void withdraw(double amount) {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("amount must be greater than zero");
	    } 
	    else if (amount > this.sumTransactions()) {
	    	throw new IllegalArgumentException("amount must be less than current balance");
	    }
	    else {
	        transactions.add(new Transaction(-amount));
	    }
	}

    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
        	case CHECKING:
        		return amount * calcualteAccruedIntrestRate(0.001);
        		
            case SAVINGS:
                if (amount <= 1000)
                    return amount * calcualteAccruedIntrestRate(0.001);
                else
                    return 1 + (amount-1000) * calcualteAccruedIntrestRate(0.002);
                
            case MAXI_SAVINGS:
                if (checkNoWithdrawlsWithinTenDays())
                	return amount * calcualteAccruedIntrestRate(0.005);
                else
                	return amount * calcualteAccruedIntrestRate(0.001);
                
            default:
            	throw new IllegalArgumentException("Error, invalid account type");
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
    
    private boolean checkNoWithdrawlsWithinTenDays() {
    	Long tenDaysInMs = (long) 8.64e+8;
    	
    	if(transactions.isEmpty())
    		return true;
    	
    	Date dateNow = DateProvider.getInstance().now(); 
    	Date lastWithdrawlDate = transactions.get(transactions.size()).getTransactionDate();
    	
    	Long timeBetweenLastWithdrawl = Math.abs(dateNow.getTime() - lastWithdrawlDate.getTime());
    	
    	if(timeBetweenLastWithdrawl > tenDaysInMs)
    		return true;
    	else
    		return false;
    }

    private double calcualteAccruedIntrestRate(double annualIntrestRate) {
    	Long DayInMs = (long) 8.64e+7;
    	
    	double dailyIntrestRate = annualIntrestRate / 365;
    	
    	Date dateNow = DateProvider.getInstance().now(); 
    	Date firstTransactionDate = transactions.get(0).getTransactionDate();
    	
    	Long timeAccountHasBeenOpenMs = Math.abs(dateNow.getTime() - firstTransactionDate.getTime());
    	int daysAccountHasBeenOpen = (int) (timeAccountHasBeenOpenMs / DayInMs);
    	
    	return
    		dailyIntrestRate*(daysAccountHasBeenOpen%365);
    }
}
