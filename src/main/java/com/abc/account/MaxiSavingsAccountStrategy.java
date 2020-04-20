package com.abc.account;

public class MaxiSavingsAccountStrategy implements AccountStrategy {

    @Override
    public double interestEarned(double amount) {
        if (amount <= 1000) {
            return amount * 0.02;
        } else if (amount <= 2000) {
            return 20 + (amount - 1000) * 0.05;
        } else {
            return 70 + (amount - 2000) * 0.1;
        }
    }
}
