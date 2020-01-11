package com.abc.accounts;

import com.abc.Account;

public class SavingsAccount extends Account {

    /**
     * interest rate for the first $1000
     * */
    private double interestRate1 = 0.001;
    private double moneyCap1 = 1000.0;

    /**
     * interest rate after the initial $1000
     */
    private double interestRate2 = 0.002;

    public SavingsAccount(AccountType accountType){
        super(accountType);
    }

    public double interestEarned() {
        double amount = sumTransactions();
        if(amount >= Double.MAX_VALUE)
        if (amount <= moneyCap1){
            return amount * interestRate1;
        }
        return (amount - moneyCap1) * interestRate2 + moneyCap1 * interestRate1 ;
    }

    public String toString(){
        return "Savings Account";
    }
    
}