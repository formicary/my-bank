package com.abc;

public class maxisavingsAccount extends Account {
	
	public maxisavingsAccount(Customer accountholder) {
		super();
		this.account_holder = accountholder;
	}
	
	protected double interestEarned() {
		double amount = getAccountBalance();
		long days = (DateProvider.getInstance().now().getTime() - latest_interestdate.getTime())/86400000;
		latest_interestdate = DateProvider.getInstance().now();
	    if(checkwithdrawlinpasttendays()) return amount*Math.pow((1+0.001/365), days) -amount;
		else return amount*Math.pow((1+0.05/365), days) -amount;
	}
	
}
