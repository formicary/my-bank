package com.abc.account;

public class CheckingAccount extends Account {
    private static final String PRETTY_ACC_NAME = "Checking Account";

    public CheckingAccount() {
        super();
    }

    @Override
    public double interestEarned() {
        double amount = sumTransactions();

        return amount * 0.001;
    }

    @Override
    public String getPrettyAccountType() {
        return PRETTY_ACC_NAME;
    }


}
