package com.abc;

import java.util.Date;

public class SavingsAccount extends Account {

    protected final double initialInterestRateAnnum;
    protected final double higherInterestRateAnnum;

    public SavingsAccount(Customer owner){
        super(owner);

        this.accountType = 1;
        this.accountTypeString = "Savings";
        this.initialInterestRateAnnum = 0.001;
        this.higherInterestRateAnnum = 0.002;
    }

    /**
     * Savings accounts have a rate of 0.1% for the first $1,000 then 0.2%
     * @return interest earned on balance
     */
    public double interestEarnedAnnum() {

        if(this.getAccountBalance() <= 1000.0) return this.getAccountBalance() * this.initialInterestRateAnnum;

        return 1 + (this.getAccountBalance()-1000.0) * this.higherInterestRateAnnum;

    }
}
