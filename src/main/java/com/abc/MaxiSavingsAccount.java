package com.abc;

public class MaxiSavingsAccount extends Account {

    MaxiSavingsAccount() {
        super(AccountType.MAXI_SAVINGS);
    }

    @Override
    double calcInterest() {
        if (getBalance() <= 1000) return getBalance() * 0.02;
        else if (getBalance() <= 2000) return 20 + (getBalance() - 1000) * 0.05;
        else return  70 + (getBalance() - 2000) * 0.1;
    }

    @Override
    String genStatement() {
        return genStatement("Maxi-Savings Account");
    }
}
