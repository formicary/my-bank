package com.abc;

import java.util.Date;

public class SavingsAccount extends Account {
	private static final double RATE_1 = 0.001;
	private static final double RATE_2 = 0.002;
	
	public SavingsAccount() {
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
    			double currentInterest = calculateInterest(i, currentSum, dayDifference);
    			System.out.println("Interest: " +currentInterest);
    			currentSum += currentInterest;
    			interest += currentInterest;
    			
    			start = DateProvider.getStartOfDay(current.getTransactionDate());
    		}
    		currentSum += current.amount;
    	}
    	transactions.remove(transactions.size()-1);
    	return interest;
    }
    
    private double calculateInterest(int currentTransaction, double currentSum, int dayDifference)
    {
    	double sum = currentSum;
		if (sum<1000)
		{
			return getInterestPerDay(currentSum, dayDifference, RATE_1);
		}
		else
		{
			for (int i = 0; i<dayDifference; i++)
			{
				sum+= getInterestPerDay(1000.0, 1, RATE_1) + getInterestPerDay(sum - 1000.0, 1, RATE_2);
			}
			return sum-currentSum;
		}
    }
    public double interestEarned()
    {
    	double total = sumTransactions();
    	return (total<1000)? total*RATE_1 : 1000*RATE_1 + (total-1000)*RATE_2;
    }
	@Override
	public String toString() {
		return "Savings Account\n";
	}
}
