package com.abc;

import java.util.concurrent.TimeUnit;

public class MaxiSavingsAccount extends Account {

	protected static final int MAXI_SAVINGS = 2;
	private double accumulatedAmount; 
	private long lastTimeUpdate; 
	private double tempAmount;
	private int minDays; 
	
	 public MaxiSavingsAccount() {
		super();
		accumulatedAmount = 0.0;
		lastTimeUpdate =  getOpeningDate(); 
		tempAmount = 0.0;
		minDays = 10;
	}
	 
	/*
	 @Override
	public double interestEarned() {
		  double amount = sumTransactions();
		  double interest = 0.05; 
		  
		  if(has_withdrawn(10) == true) {
			  interest = 0.001;
		  }
		  
		  return amount * interest;
	 }*/


	@Override
	public void update(long time, double amount) {
		
		 long diff =  time - lastTimeUpdate;
    	 long days = TimeUnit.DAYS.convert((long) diff, TimeUnit.MILLISECONDS);
    	 
		 double interest = 0.05;

		 if(days > 0) {
			 interest = getInterest(days); 
			 accumulatedAmount += interest * sumTransactions();
			 tempAmount = 0.0;
		 }
		 else {
			 interest = getInterest(1); 
			 accumulatedAmount -= tempAmount;
    		 tempAmount = interest * sumTransactions();
    		 accumulatedAmount += tempAmount;
		 }
		 
		 lastTimeUpdate = time;
	}
		
	 private double getInterest(double days) {
		 double interest = 0.0;
		 
		 if(minDays < days) {
			interest += (days - minDays) * 0.05;
			interest += minDays * 0.001;	 
		 }
		 else {
			 interest += days * 0.001;
		 }
		 
		 return interest; 
	 }
	 
	 public double getInterestEarned() {
			return accumulatedAmount;
	}
	 
	@Override
	public int getAccountType() {
		return MAXI_SAVINGS;
	}

}
