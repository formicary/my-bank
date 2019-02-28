package com.abc;

import java.lang.Math;

public class MaxiSavingsAccount extends Account {

    public static final double INTEREST_RATE_0 = 0.02;
    public static final double INTEREST_RATE_1 = 0.05;
    public static final double INTEREST_RATE_2 = 0.1;
    public static final double THRESHOLD_1 = 1000.0;
    public static final double THRESHOLD_2 = 2000.0;

    public String accountType() {
        return "Maxi Savings Account";
    }

    public double dailyInterest(double balance) {
        double principal0 = Math.min(balance, THRESHOLD_1);
        double principal1 = Math.max(0, Math.min(balance, THRESHOLD_2) - THRESHOLD_1);
        double principal2 = Math.max(0, balance - THRESHOLD_2);

        return principal0 * INTEREST_RATE_0 / 365
               + principal1 * INTEREST_RATE_1 / 365
               + principal2 * INTEREST_RATE_2 / 365;
    }

}
