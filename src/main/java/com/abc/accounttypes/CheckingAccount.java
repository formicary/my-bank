package com.abc.accounttypes;

public class CheckingAccount implements AccountType {
    private static double interestRate = 0.001;

    public double interestEarned(double amount) {
        return amount * interestRate;
    }
    
}