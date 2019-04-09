package com.abc.domain;

public class MockAccount extends Account {

    public double balance;
    public double interest;

    public MockAccount() {
        super(new MockAccountType());
    }

    public MockAccountType getMockAccountType() {
        return (MockAccountType) getAccountType();
    }

    @Override
    public double getBalance() {
        return balance;
    }

    @Override
    public double interestEarned() {
        return interest;
    }

}
