package com.abc;

public class SavingsAccount extends Account {

    protected final double initialInterestRate;
    protected final double higherInterestRate;

    public SavingsAccount(Customer owner){
        super(owner);

        this.accountType = 1;
        this.accountTypeString = "Savings";
        this.initialInterestRate = 0.001;
        this.higherInterestRate = 0.002;
    }

    /**
     * Savings accounts have a rate of 0.1% for the first $1,000 then 0.2%
     * @return interest earned on balance
     */
    public double interestEarned() {

        if(this.getAccountBalance() <= 1000.0) return this.getAccountBalance() * this.initialInterestRate;

        return 1 + (this.getAccountBalance()-1000.0) * this.higherInterestRate;

    }
}
