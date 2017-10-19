package com.abc.accounts;

public class CheckingAccount extends Account {

    private static double FLAT_INTEREST_RATE = 0.001;

    public CheckingAccount() {
        super();
    }

    public double interestEarned() {
        double amount = sumTransactions();
        return amount * FLAT_INTEREST_RATE;

    }

    protected String getPrettyAccountType() {
        return "Checking Account\n";
    }

}
