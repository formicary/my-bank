package com.abc.accounts;

public class SavingsAccount extends Account {

    private static double TIER_1_ANNUAL_INTEREST_RATE = 0.001;
    private static double TIER_2_ANNUAL_INTEREST_RATE = 0.002;
    private static double TIER_1_DAILY_INTEREST_RATE = TIER_1_ANNUAL_INTEREST_RATE / 365.0;
    private static double TIER_2_DAILY_INTEREST_RATE = TIER_2_ANNUAL_INTEREST_RATE / 365.0;


    public SavingsAccount() {
        super();
    }

    protected double getDailyInterest(double balance) {
        if (balance <= 1000) {
            return balance * TIER_1_DAILY_INTEREST_RATE;
        } else {
            return 1000 * TIER_1_DAILY_INTEREST_RATE + (balance - 1000) * TIER_2_DAILY_INTEREST_RATE;
        }
    }

    protected String getPrettyAccountType() {
        return "Savings Account\n";
    }
}
