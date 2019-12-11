package com.abc;

import java.util.concurrent.TimeUnit;

public class CheckingAccount extends Account {

	protected static final int CHECKING = 0;
	private static final double INTEREST = 0.001;
	private double accumulatedAmount; 
	private long lastTimeUpdate; 
	private double tempAmount;
	
	public CheckingAccount() {
		super();
		accumulatedAmount = 0.0;
		lastTimeUpdate =  getOpeningDate(); 
		tempAmount = 0.0;
	}

	@Override
	public int getAccountType() {
		return CHECKING;
	}
	
	public void update(long time, double amount){
	
    	 long diff =  time - lastTimeUpdate;
    	 long days = TimeUnit.DAYS.convert((long) diff, TimeUnit.MILLISECONDS);
    	 
    	 if(days > 0) {
    		 accumulatedAmount += INTEREST * days * sumTransactions();
    		 tempAmount = 0.0;
    	 }
    	 else {
    		 accumulatedAmount -= tempAmount;
    		 tempAmount = INTEREST * sumTransactions();
    		 accumulatedAmount += tempAmount;
    	 }
    	 lastTimeUpdate = time;
    }

	@Override
	public double getInterestEarned() {
		return accumulatedAmount;
	}

}
