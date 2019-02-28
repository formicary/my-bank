package com.abc;

import java.lang.Math;

public class SavingsAccount extends Account {

    public static final double INTEREST_RATE_0 = 0.001;
    public static final double INTEREST_RATE_1 = 0.002;
    public static final double THRESHOLD_1 = 1000.0;

    public String accountType() {
        return "Savings Account";
    }

    public double dailyInterest(double balance) {
        return Math.min(balance, THRESHOLD_1) * INTEREST_RATE_0 / 365
               + Math.max(0, balance - THRESHOLD_1) * INTEREST_RATE_1 / 365;
    }

}
