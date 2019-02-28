package com.abc;

public class CheckingAccount extends Account {

    public static final double INTEREST_RATE = 0.001;

    public String accountType() {
        return "Checking Account";
    }

    public double dailyInterest(double balance) {
        return balance * INTEREST_RATE / 365;
    }

}
