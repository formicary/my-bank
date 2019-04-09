package com.abc.domain;

public class MockCustomer extends Customer {

    public double interest = 0.0d;
    public String shortDescription = "Mock (2 accounts)";

    public MockCustomer(String name) {
        super(name);
    }

    @Override
    public double totalInterestEarned() {
        return interest;

    }

    @Override
    public String getShortDescription() {
        return shortDescription;
    }
}
