package com.abc;

import java.util.Date;

public class CheckingAccount extends Account {

    private final double interestRateAnnum = 0.001;
    private final double interestRateDaily = 0.001 / 365.0;


    public CheckingAccount(Customer owner){
        super(owner);
        this.accountType = 0;
        this.accountTypeString = "Checking";
    }

    public double interestEarnedAnnum() {
        return this.getAccountBalance() * this.interestRateAnnum;
    }

}
