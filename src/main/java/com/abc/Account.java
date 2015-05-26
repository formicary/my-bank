package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Calendar;
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
	    } else {
	        transactions.add(new Transaction(-amount));
	    }
	}
    
    public double interestEarned() {
        double amount = sumTransactions();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * calculateAPY(0.001);
                else
                    return 1 + (amount-1000) * calculateAPY(0.002);
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
            	/*
                if (amount <= 1000)
                    return amount * 0.02;
                if (amount <= 2000)
                    return 20 + (amount-1000) * 0.05;
                return 70 + (amount-2000) * 0.1;
                */
            	//Change **Maxi-Savings accounts** to have an interest rate of 5% assuming no withdrawals in the past 10 days
            	Date tenDaysAgo = new Date(Calendar.getInstance().getTimeInMillis()-10*24*60*60*1000);
            	for(Transaction t : transactions) {
            		if(t.getTransactionDate().after(tenDaysAgo)) {
            			return amount * calculateAPY(0.01);
            		}
            	}
            	return amount * calculateAPY(0.05);
            default:
                return amount * calculateAPY(0.001);
        }
    }
    
    //Interest rates should accrue daily (incl. weekends), rates above are per-annum
    public static double calculateAPY(double rate) {
    	// APY = (1+ APR / n)^n - 1 where n equals to the number of compounding periods.
    	int periods = 365; //to manage better, ex: step years, etc...
    	BigDecimal one = new BigDecimal(rate).divide(new BigDecimal(periods),RoundingMode.HALF_UP);
    	one = one.add(new BigDecimal(1));
    	one = one.pow(periods);
    	one = one.subtract(new BigDecimal(1));
    	return one.doubleValue();
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

}
