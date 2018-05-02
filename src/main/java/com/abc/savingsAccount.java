package com.abc;

public class savingsAccount extends Account {

	public savingsAccount(Customer accountholder) {
		this.account_holder = accountholder;
	}
	
	protected double interestEarned() {
		double amount = getAccountBalance();
		long days = (DateProvider.getInstance().now().getTime() - latest_interestdate.getTime())/86400000;
		latest_interestdate = DateProvider.getInstance().now();
	    return amount * Math.pow((1+0.001/365),days) - amount;
	}
	
}
