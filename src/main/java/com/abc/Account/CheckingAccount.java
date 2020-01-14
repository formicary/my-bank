package com.abc.Account;

public class CheckingAccount extends Account {


    public double earnedInterest() {
        return getBalance() * 0.001;
    }

    public String getAccountType() {
        return "Checking Account\n";
    }


}
