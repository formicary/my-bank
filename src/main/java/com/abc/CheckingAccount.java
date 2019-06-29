package com.abc;

/**
 * Implementation of Account for Checking Accounts
 */
public class CheckingAccount extends Account {

    private final double interestRateDaily = 2.7397260273972604E-6;


    public CheckingAccount(Customer owner){
        super(owner);
        this.accountType = 0;
        this.accountTypeString = "Checking";
    }

    public double interestEarned() {
        double interestRate = 0.001;
        return this.getAccountBalance() * interestRate;
    }

}
