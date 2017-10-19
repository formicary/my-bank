package com.abc.accounts;

public class MaxiSavingsAccount extends Account {

    private static double TIER_1_INTEREST_RATE = 0.02;
    private static double TIER_2_INTEREST_RATE = 0.05;
    private static double TIER_3_INTEREST_RATE = 0.1;

    public MaxiSavingsAccount() {
        super();
    }

    public double interestEarned() {
        double amount = sumTransactions();
        if (amount <= 1000)
            return amount * TIER_1_INTEREST_RATE;
        else if (amount <= 2000)
            return 20 + (amount-1000) * TIER_2_INTEREST_RATE;
        else
            return 70 + (amount-2000) * TIER_3_INTEREST_RATE;
    }

    protected String getPrettyAccountType() {
        return "Maxi Savings Account\n";
    }


}
