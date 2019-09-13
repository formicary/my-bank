package com.abc.Accounts;

public class CheckingAccount extends Account {

    private static final String ACCOUNT_NAME = "Checking Accounts";

    public String getName(){
        return ACCOUNT_NAME;
    }

    public double interestEarned() {
        double amount = sumTransactions();
        return amount*0.001;
    }

}
