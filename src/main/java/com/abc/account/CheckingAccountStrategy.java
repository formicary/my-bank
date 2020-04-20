package com.abc.account;

public class CheckingAccountStrategy implements AccountStrategy {

    @Override
    public double interestEarned(double amount) {
        return amount * 0.001;
    }
}
