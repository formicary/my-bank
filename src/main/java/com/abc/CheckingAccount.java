package com.abc;

import java.util.Date;

public class CheckingAccount extends Account {
	private static final double RATE = 0.001;
	public CheckingAccount()
	{
		super();
	}
	
    public double dailyInterestEarned()
    {
    	if (transactions.size() == 0)
    		return 0.0;
    	
    	double interest = 0.0;
    	
    	Date start = transactions.get(0).getTransactionDate();
    	start = DateProvider.getStartOfDay(start);
    	double currentSum = transactions.get(0).amount;
    	
    	transactions.add(new Transaction(0.1, DateProvider.now()));
    	for (int i = 1; i < transactions.size(); i++)
    	{
    		Transaction current = transactions.get(i);
    		int dayDifference = (int) DateProvider.getDifferenceDays(start, current.getTransactionDate());
    		
    		if (dayDifference > 0)
    		{
    			double currentInterest = getInterestPerDay(currentSum, dayDifference, RATE);
    			
    			currentSum += currentInterest;
    			interest += currentInterest;
    			
    			start = DateProvider.getStartOfDay(current.getTransactionDate());
    		}
    		currentSum += current.amount;
    		
    	}
    	transactions.remove(transactions.size()-1);
    	
    	return interest;
    }
    public double interestEarned()
    {
    	return sumTransactions()*0.001;
    }

	@Override
	public String toString() {
		return "Checking Account\n";
	}
}
