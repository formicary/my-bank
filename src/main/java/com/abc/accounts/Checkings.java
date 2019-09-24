package com.abc.accounts;

/**
 * @project MyBank
 */
public class Checkings extends Account {
    @Override
    public double interestEarned() {
        return sumTransactions() * 0.001;
    }

    @Override
    public String toString() {
        return "Checking Account";
    }
}
