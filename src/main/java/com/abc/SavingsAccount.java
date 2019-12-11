package com.abc;

import java.util.concurrent.TimeUnit;

public class SavingsAccount extends Account {
	
	protected static final int SAVINGS = 1;
	private double accumulatedAmount; 
	private long lastTimeUpdate; 
	private double tempAmount;
	
	public SavingsAccount() {
		super();
		accumulatedAmount = 0.0;
		lastTimeUpdate =  getOpeningDate(); 
		tempAmount = 0.0;
	}

	public void update(long time, double amount){
		
	 double balance = sumTransactions();
	 
   	 long diff =  time - lastTimeUpdate;
   	 long days = TimeUnit.DAYS.convert((long) diff, TimeUnit.MILLISECONDS);
   	 
   	 double interest = 0.002; 
   	
	  if(days == 0) 
	   		days = 1;
	  else
	   		tempAmount = 0.0; 
   		
	  accumulatedAmount -= tempAmount;
	  
	  if(balance <= 1000) {
		  interest = 0.001; 
		  tempAmount = 0*days + interest * days * (balance - 0);
	  }
	  else {
		  tempAmount = 1*days + interest * days * (balance-1000);
	  }
	 
	  accumulatedAmount += tempAmount;
   	
   	  lastTimeUpdate = time;
   }
	
	public double getInterestEarned() {
		return accumulatedAmount;
	}

	@Override
	public int getAccountType() {
		return SAVINGS;
	}
}
