package com.abc;

public class SavingsAccount extends Account {

    private static final double LOWER_LIMIT_INTEREST_RATE = 0.001;
    private static final double UPPER_LIMIT_INTEREST_RATE = 0.002;

    /**
     * SavingsAccount Constructor
     */
    public SavingsAccount(){
        super();
    }

    /**
     * InterestEarned method calculates the interest for this type of Account (Savings)
     * @return Returns the interest earned for this type of account.
     */
    public double interestEarned() {
        double amount = sumOfAllTransactions();

        if (amount <= 1000)
            return amount * LOWER_LIMIT_INTEREST_RATE;
        else
            return 1 + (amount-1000) * UPPER_LIMIT_INTEREST_RATE;
    }
}


