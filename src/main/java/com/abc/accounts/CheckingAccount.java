package com.abc.accounts;

import java.sql.Date;

public class CheckingAccount extends Account {
    /**
     * Flat interest rate of 0.1% per-annumm
     */
    private static double interestRate = 0.001;


    public CheckingAccount(AccountType accountType){
        super(accountType);
        currentInterest = 0.0;
    }

    public double interestEarned() {

        
        // currentInterest = (currentInterest + sumTransactions()) * interestRate / dateProvider.getYearDays() ;
        return currentInterest;
    }

    public String toString(){
        return "Checking Account";
    }
    
}