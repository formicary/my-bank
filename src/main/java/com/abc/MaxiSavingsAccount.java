package com.abc;

import java.util.Date;

public class MaxiSavingsAccount extends Account{

	private static final double RATE_BASE = 0.001;
	private static final double RATE_NO_WITHDRAWALS = 0.05;
	
	private static final double[] RATES = {0.02, 0.05, 0.1};
	
	public MaxiSavingsAccount() {
		super();
	}

    public double dailyInterestEarned()
    {

    	if (transactions.size() == 0)
    		return 0.0;
    	
    	double interest = 0.0;
    	
    	Date start = transactions.get(0).getTransactionDate();
    	start = DateProvider.getStartOfDay(start);
    	
    	// first will always be deposit and no withdrawals before, so start with high rates
    	Date lastWithdrawal = DateProvider.getDateWithOffset(start, -10);
    	
    	double currentSum = transactions.get(0).amount;
    	transactions.add(new Transaction(1.0, DateProvider.now()));
    	
    	for (int i = 1; i < transactions.size(); i++)
    	{
    		Transaction current = transactions.get(i);

    		int dayDifference = (int) DateProvider.getDifferenceDays(start, current.getTransactionDate());
    		// catch up with interest
    		if (dayDifference > 0)
    		{
    			double currentInterest = calculateInterest(i, lastWithdrawal, currentSum);
    			
    			currentSum += currentInterest;
    			interest += currentInterest;
    			
    			start = DateProvider.getStartOfDay(current.getTransactionDate());
    			
    			
    		}
    		currentSum += current.amount;

    		if (current.amount<0)
    			lastWithdrawal = DateProvider.getStartOfDay(current.getTransactionDate());
    	}
    	
    	transactions.remove(transactions.size()-1);
    	return interest;
    }
    
    /*
     * calculate all interest from previous transaction to transactionPosition transaction
     * to calculate remaining interest from last transaction, pass in transactionPosition larger or equal to transactions size
     */
    private double calculateInterest(int transactionPosition, Date lastWithdrawal, double currentSum)
    {
    	double interest = 0.0;
    	double sum = currentSum;
    	
    	Transaction previous = transactions.get(transactionPosition-1);
    	Transaction current = transactions.get(transactionPosition);
    	
    	int previousToCurrent = (int) DateProvider.getDifferenceDays(previous.getTransactionDate(), current.getTransactionDate());
    	int lastWithdrawToPrevious = (int) DateProvider.getDifferenceDays(lastWithdrawal, previous.getTransactionDate());

    	int baseRateDays = 10 - lastWithdrawToPrevious;
    	baseRateDays = (baseRateDays<0)? 0 : baseRateDays;

        int highRateDays = previousToCurrent - baseRateDays;
        highRateDays = (highRateDays<0)? 0 : highRateDays;
        
        interest+=getInterestPerDay(sum, baseRateDays, RATE_BASE);
        sum+=interest;
        	
        interest+=getInterestPerDay(sum, highRateDays, RATE_NO_WITHDRAWALS);
    	
        System.out.println("base: " +baseRateDays + " high :" +highRateDays);
    	return interest;
    }
    
    
    public double interestEarned()
    {
    	double total = sumTransactions();
    	if (total < 1000)
    		return total*RATES[0];
    	else if (total<2000)
    		return 1000*RATES[0] + (total-1000)*RATES[1];
    	else 
    		return 1000*RATES[0] + 1000*RATES[1] + (total - 2000)*RATES[2];
    }

	@Override
	public String toString() {
		return "Maxi Savings Account\n";
	}
    
}
