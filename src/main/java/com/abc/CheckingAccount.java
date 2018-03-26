package com.abc;

public class CheckingAccount extends Account {

    public static final double interestRate = 0.001;

    public CheckingAccount() {
        super();
        accountType = 0; // Checking Account
    }

    @Override
    public double calculateInterest(double diffInDays, double balance) {
        return (balance * Math.pow((1 + interestRate / 365), diffInDays)) - balance;
    }

}