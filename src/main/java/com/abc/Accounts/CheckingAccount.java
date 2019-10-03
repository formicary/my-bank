package com.abc.Accounts;

public class CheckingAccount extends Account {


    public double interestEarned() {
        return getBalance() * 0.001;
    }

    public String getAccountType() {
        return "Checking Account\n";
    }


}
