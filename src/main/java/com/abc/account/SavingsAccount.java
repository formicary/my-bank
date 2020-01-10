package com.abc.account;

public class SavingsAccount extends Account {
    private static final String PRETTY_ACC_NAME = "Savings Account";

    public SavingsAccount() {
        super();
    }

    @Override
    public double interestEarned() {
        double interest;
        double amount = sumTransactions();
        if (amount <= 1000) {
            interest = amount * 0.001;
        }else {
            interest = 1 + (amount - 1000) * 0.002;
        }
        return interest;
    }

    @Override
    public String getPrettyAccountType() {
        return PRETTY_ACC_NAME;
    }
}
