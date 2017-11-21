package com.abc;

public class MaxiSavingsAccount extends Account {

    private static final double LOWER_LIMIT_INTEREST_RATE = 0.001;
    private static final double UPPER_LIMIT_INTEREST_RATE = 0.05;

    /**
     * MaxiSavingsAccount constructor
     */
    public MaxiSavingsAccount() {
        super();
    }

    /**
     * InterestEarned method calculates the interest for this type of Account (Maxi Savings)
     * @return Returns the interest earned for this type of account.
     */
    public double interestEarned() {
        double amount = sumOfAllTransactions();

        if(!checkWithdrawTransactionForLast10Days())
            return amount * UPPER_LIMIT_INTEREST_RATE;
        else
            return amount * LOWER_LIMIT_INTEREST_RATE;
    }
}
