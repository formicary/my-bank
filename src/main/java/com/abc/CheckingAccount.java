package com.abc;

public class CheckingAccount extends Account{

	private static double interestRate;
	
	public CheckingAccount(){
		super(AccountType.CHECKING);
		interestRate = 0.001;
	}

	public double interestEarned() {
		double amount = sumTransactions();
        
        return amount * interestRate;
	}
	
	public static void setInterestRate(double newInterestRate){
		interestRate = newInterestRate;
	}
	
	public static double getInterestRate(){
		return interestRate;
	}
}
