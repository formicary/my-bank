package com.abc.accounts;

public class CheckingAccount extends Account {

    private static double FLAT_INTEREST_RATE = 0.001;
    private static double DAILY_INTEREST_RATE = FLAT_INTEREST_RATE / 365.0;

    public CheckingAccount() {
        super();
    }

    protected double getDailyInterest(double balance) {
        return balance * DAILY_INTEREST_RATE;
    }

    protected String getPrettyAccountType() {
        return "Checking Account\n";
    }

}
