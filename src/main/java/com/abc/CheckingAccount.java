package com.abc;

public class CheckingAccount extends Account {

    private final double interestRate = 0.001;

    public CheckingAccount(){
        this.accountType = 0;
        this.accountTypeString = "Checking";
    }

    public double interestEarned() {

        // balance of
        if(this.getAccountBalance() <= 0) return 0.0;

        return this.getAccountBalance() * this.interestRate;
    }
}
