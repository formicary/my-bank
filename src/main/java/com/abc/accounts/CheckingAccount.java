package com.abc.accounts;
import com.abc.Account;

public class CheckingAccount extends Account {
    /**
     * Flat interest rate of 0.1%
     */
    private static double interestRate = 0.001;

    public CheckingAccount(AccountType accountType){
        super(accountType);
    }

    public double interestEarned() {
        return sumTransactions() * interestRate;
    }

    public String toString(){
        return "Checking Account";
    }
    
}