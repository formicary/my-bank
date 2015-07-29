package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Account {

	public static final int CHECKING = 0;
	public static final int SAVINGS = 1;
	public static final int MAXI_SAVINGS = 2;

	// Values used for calculating interest
	private final double CheckingInterest = 0.01/365;
	private final double SavingsInterest = 0.02/365;
	private final double Maxi_SavingsInterest = 0.5/365;
	
	Timer timer = new Timer(); // timer used to schedule interest
	public static long day = 86400000; // milliseconds in a day
	
	private final double savingMax = 1000 * CheckingInterest;
	boolean Maxi = true; // Effects interest for maxi savings accounts
	Timer maxiTimer = new Timer(); // timer for maxi savings accounts
	
	private final int accountType;
	public List<Transaction> transactions;
	
	// Account values
	public double amount = 0.0; // removes the need to calculate new total amount repetitively 
	public double totalInterest = 0.0;

	public Account(int accountType) {
		this.accountType = accountType;
		this.transactions = new ArrayList<Transaction>();
		
		// setup timer to compound interest at midnight each day
		timer.scheduleAtFixedRate(new TimerTask(){
			@Override
			public void run() {
				compoundInterest();	
			}
			
		}, DateProvider.tillMidnight(), day);
	}

	public void deposit(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			this.amount += amount;
			transactions.add(new Transaction(amount));
		}
	}

	public void withdraw(double amount) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else {
			this.amount -= amount;
			transactions.add(new Transaction(-amount));
			if(accountType == MAXI_SAVINGS){
				Maxi = false;
				
				// Schedules a timer to reactivate Maxi Savings after 10 days
				maxiTimer.cancel();
				maxiTimer = new Timer();
				Date date = DateProvider.then(10);
				maxiTimer.schedule(new TimerTask(){
					@Override
					public void run() {
						Maxi = true;
						this.cancel();
					}
					
				}, date);
			}
		}
	}
	
	
	// Replaced with the function compoundInterest()
//	public double interestEarned() {
//		switch(accountType){
//		case SAVINGS:
//			if (amount <= 1000)
//				return amount * 0.01;
//			else
//				return 1 + (amount-1000) * 0.02;
//			//            case SUPER_SAVINGS:
//				//                if (amount <= 4000)
//			//                    return 20;
//		case MAXI_SAVINGS:
//			if (amount <= 1000)
//				return amount * 0.02;
//			if (amount <= 2000)
//				return 20 + (amount-1000) * 0.05;
//			return 70 + (amount-2000) * 0.1;
//		default:
//			return amount * 0.001;
//		}
//	}

	public double sumTransactions() {
		double amount = 0.0;
		for (Transaction t: transactions)
			amount += t.amount;
		return amount;
		//  return checkIfTransactionsExist(true); - unnecessary function call
	}

	// unnecessary function
	//    private double checkIfTransactionsExist(boolean checkAll) {
	//        double amount = 0.0;
	//        for (Transaction t: transactions)
	//            amount += t.amount;
	//        return amount;
	//    }

	public int getAccountType() {
		return accountType;
	}

	private void compoundInterest(){
		double interest = 0.0;
		switch(accountType){
		case CHECKING:
			interest = amount * CheckingInterest;
			break;
		case SAVINGS:
			if (amount <= 1000)
				interest = amount * CheckingInterest;
			else
				interest = savingMax + (amount-1000) * SavingsInterest;
			break;
		case MAXI_SAVINGS:
			if(Maxi){
				interest = amount * Maxi_SavingsInterest;
			} else {
				interest = amount * CheckingInterest;
			}
			break;
		}
			
		totalInterest += interest;
		amount += interest;
	}
	
}
