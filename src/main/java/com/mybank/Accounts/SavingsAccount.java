package com.mybank.Accounts;

import com.mybank.Utlities.AccountType;

public class SavingsAccount extends Account {
    public SavingsAccount() {
        super(AccountType.SAVINGS);
    }

    @Override
    public double interestEarned() {
        double amount = sumTransactions();
        if (amount <= 1000)
            return amount * 0.001;
        else
            return (amount - 1000) * 0.002;
    }

}
