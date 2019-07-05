package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Account {

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;

    private final int accountType;
    public List<Transaction> transactions;
    public Date dateWithdraw;
	public Date currentDate;
	//Date of last time interest was collected.
	public Date dateLast;
	private double runningInterest;
    

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.dateWithdraw = DateProvider.getInstance().now();
        this.currentDate = DateProvider.getInstance().now();
        this.dateLast = DateProvider.getInstance().now();
        this.runningInterest = 0.0;
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
    		//Change date to current when withdrawal is successful 
    		dateWithdraw = DateProvider.getInstance().now();
    		transactions.add(new Transaction(-amount));
    	}
    }
    
    //check difference between dates
    private static long dateDifference(Date one, Date two) {
    	long difference =  (one.getTime()-two.getTime())/86400000;
    	return Math.abs(difference);
    }

    public double runningInterest() {
    	long gap = dateDifference(dateLast, currentDate);
    	dateLast = currentDate;
    	//Don't get interest until at least 1 day has passed
    	while(gap > 0) {
    		runningInterest = runningInterest + interestEarned();
    		gap--;
    	}
    	return runningInterest;
    }
    
    public double interestEarned() {
        double amount = sumTransactions() + runningInterest;
        currentDate = DateProvider.getInstance().now();
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * 0.001;
                else
                    return 1 + (amount-1000) * 0.002;
            //Check date of last transaction, if greater than 10 days ago set to 5% 
            case MAXI_SAVINGS:
                if (dateDifference(currentDate, dateWithdraw) >= 10)
                    return amount * 0.05;
				else
                	return amount * 0.001;
            default:
                return amount * 0.001;
        }
    }

    public double sumTransactions() {
       return checkIfTransactionsExist();
    }

    private double checkIfTransactionsExist() {
        double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.amount;
        return amount;
    }

    public int getAccountType() {
        return accountType;
    }

}
