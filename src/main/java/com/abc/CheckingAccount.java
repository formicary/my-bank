package com.abc;

public class CheckingAccount extends Account {

    private static final double LOWER_LIMIT_INTEREST_RATE = 0.001;

    /**
     * CheckingAccount constructor
     */
    public CheckingAccount(){
        super();
    }

    /**
     * InterestEarned method calculates the interest for this type of Account (Checking)
     * @return Returns the interest earned for this type of account.
     */
    public double interestEarned() {
        return sumOfAllTransactions() * LOWER_LIMIT_INTEREST_RATE;
    }
}
