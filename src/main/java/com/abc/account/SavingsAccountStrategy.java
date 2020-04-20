package com.abc.account;

public class SavingsAccountStrategy implements AccountStrategy {

    @Override
    public double interestEarned(double amount) {
        if (amount <= 1000) {
            return amount * 0.001;
        } else {
            return 1 + (amount - 1000) * 0.002;
        }
    }
}
