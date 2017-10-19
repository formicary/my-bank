package com.abc.accounts;

public class SavingsAccount extends Account {

    private static double TIER_1_INTEREST_RATE = 0.001;
    private static double TIER_2_INTEREST_RATE = 0.002;

    public SavingsAccount() {
        super();
    }

    public double interestEarned() {
        double amount = sumTransactions();
        if (amount <= 1000)
            return amount * TIER_1_INTEREST_RATE;
        else
            return 1 + (amount-1000) * TIER_2_INTEREST_RATE;

    }

    public int getAccountType() {
        return Account.SAVINGS;
    }
}
