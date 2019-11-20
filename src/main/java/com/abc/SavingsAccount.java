package com.abc;

public class SavingsAccount extends Account {

    SavingsAccount() {
        super(AccountType.SAVINGS);
    }

    @Override
    double calcInterest() {
        return (getBalance() <= 1000) ? getBalance() * 0.001 : 1 + (getBalance() - 1000) * 0.002;
    }

    @Override
    String genStatement() {
        return genStatement("Savings Account");
    }
}
