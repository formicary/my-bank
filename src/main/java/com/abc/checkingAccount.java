package com.abc;

public class checkingAccount extends Account {
	
	public checkingAccount(Customer accountholder) {
		super();
		this.account_holder = accountholder;
	}
	
	
	protected double interestEarned() {
		double amount = getAccountBalance();
		long days = (DateProvider.getInstance().now().getTime() - latest_interestdate.getTime())/86400000;
		latest_interestdate = DateProvider.getInstance().now();
	    if (amount <= 1000)
            return amount *Math.pow((1+0.001/365),days) - amount;
        else
        		return 1000*Math.pow((1+0.001/365), days) + (amount-1000) * Math.pow((1+0.002/365),days) - amount;
	}
	
}
