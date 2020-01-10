package com.abc.account;

public class MaxiSavingsAccount extends Account {
    private static final String PRETTY_ACC_NAME = "Maxi Savings Account";

    public MaxiSavingsAccount() {
        super();
    }

    @Override
    public double interestEarned() {
        double interest;
        double amount = sumTransactions();

        if (amount <= 1000) {
            interest = amount * 0.02;
        } else if (amount <= 2000) {
            interest = 20 + (amount - 1000) * 0.05;
        } else {
            interest = 70 + (amount - 2000) * 0.1;
        }

        return interest;
    }

    @Override
    public String getPrettyAccountType() {
        return PRETTY_ACC_NAME;
    }

}
