package com.abc;

import java.lang.Math;

public class CheckingAccount extends Account {
    
    public CheckingAccount() {
        super();
        accountName = "Checking Account";
    }

    @Override
    public double interestEarned() {
        return balance * 0.001;
    }

}