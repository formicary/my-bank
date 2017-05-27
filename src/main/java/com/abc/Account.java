package com.abc;


import java.util.ArrayList;
import java.util.List;

public class Account {
	
	public static final double savingsInterestLow = 0.001;
	public static final double savingsInterestHigh = 0.002;
	public static final double maxiSavingsInterest = 0.001;
	public static final double maxiSavingsInterestTenDays = 0.05;
	public static final double checkingInterest = 0.001;
	public static final double daysPerAnnum = 365.0;

    public static final int CHECKING = 0;
    public static final int SAVINGS = 1;
    public static final int MAXI_SAVINGS = 2;
    
    private double money;

    private final int accountType;
    public List<Transaction> transactions;

    public Account(int accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        this.money = 0.0;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
        	money += amount;
            transactions.add(new Transaction(amount));
        }
    }

	public void withdraw(double amount) {
	    if (amount <= 0) {
	        throw new IllegalArgumentException("amount must be greater than zero");
	    } else {
	    	if(amount <= money){
	    		money -= amount;
	    		transactions.add(new Transaction(-amount));
	    	}else{
	    		throw new IllegalArgumentException("you do not have sufficient funds for this transaction");
	    	}
	    }
	}

    public double interestEarned() {
    	//keep money in the account and get it instead of iterating transactions
        double amount = money;

        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return (amount * savingsInterestLow)/daysPerAnnum;
                else
                    return (1 + (amount-1000) * savingsInterestHigh)/daysPerAnnum;
//            case SUPER_SAVINGS:
//                if (amount <= 4000)
//                    return 20;
            case MAXI_SAVINGS:
            	if (DateProvider.tenDaysPassed(10, transactions))
            		return (amount * maxiSavingsInterestTenDays)/daysPerAnnum;
            	else
            		 return (amount * maxiSavingsInterest)/daysPerAnnum;
            default:
                return (amount * checkingInterest)/daysPerAnnum;
        }
    }

    public int getAccountType() {
        return accountType;
    }
    
    public double getMoney(){
    	return money;
    }
    

}
