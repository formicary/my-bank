package com.abc.Account;

public class CheckingAccount extends Account {

    private static final String ACCOUNT_NAME = "Checking Account";

    public String getName(){
        return ACCOUNT_NAME;
    }

    public double interestEarned() {
        double amount = calculateBalance();
        return amount*0.001;
    }

}
