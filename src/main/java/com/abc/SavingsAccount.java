package com.abc;

/**
 * Implementation of Account specifically for a Savings Account
 */
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

        // use lower interest rate if account balance is under a grand
        if(this.getAccountBalance() <= 1000.0) return this.getAccountBalance() * this.initialInterestRate;

        // use higher interest rate on amount over a grand
        return 1 + (this.getAccountBalance()-1000.0) * this.higherInterestRate;

    }
}
