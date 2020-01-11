package com.abc.accounttypes;

public class SavingsAccount implements AccountType{

    /**
     * interest rate for the first $1000
     * */
    private double interestRate1 = 0.001;
    private double moneyCap1 = 1000.0;

    private double interestRate2 = 0.002;

    
    public double interestEarned(double amount) {
        if (amount <= moneyCap1){
            return amount * interestRate1;
        }
        return (amount - moneyCap1) * interestRate2 + moneyCap1 * interestRate1 ;

        
    }
    
}