package com.abc.accounttypes;

//TODO: consider making this a Factory
public interface AccountType {

    public double interestEarned(double amount);

    public String toString();
}