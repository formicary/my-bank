package com.abc;

public class CheckingAccount extends Account {


    public double interestEarned() {
        return sumTransactions() * 0.001;
    }

    public String getAccountType() {
        return "Checking Account\n";
    }


}
