package com.abc;

public class SavingAccount extends Account{

	private static double lowerInterestRate = 0.001, upperInterestRate = 0.002;
	private static int upperInterestRateThreshold = 1000;
	
	public SavingAccount(){
		super(AccountType.SAVING);
	}
	
	public double interestEarned() {
		double amount = sumTransactions();
        
        if (amount <= upperInterestRateThreshold){
            return amount * lowerInterestRate;
        }
        
        return (upperInterestRateThreshold * lowerInterestRate) + ((amount-upperInterestRateThreshold) * upperInterestRate);
	}
	
	public static void setLowerInterestRate(double newInterestRate){
		lowerInterestRate = newInterestRate;
	}

	public static void setUpperInterestRate(double newInterestRate){
		upperInterestRate = newInterestRate;
	}
	
	public static void setUpperInterestThreshold(int newInterestRateThreshold){
		upperInterestRateThreshold = newInterestRateThreshold;
	}
	
	public static double getLowerInterestRate(){
		return lowerInterestRate;
	}

	public static double getUpperInterestRate(){
		return upperInterestRate;
	}
	
	public static int getUpperInterestThreshold(){
		return upperInterestRateThreshold;
	}
}
