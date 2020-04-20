package com.abc.account;

public class SuperSavingsAccountStrategy implements AccountStrategy {
    @Override
    public double interestEarned(double amount) {
        if (amount <= 4000) {
            return 20;
        }

        //TODO
        return 0;
    }
}
