package com.mybank.Accounts;

import com.mybank.Utlities.AccountType;

public class CheckingAccount extends Account {
    public CheckingAccount() {
        super(AccountType.CHECKING);
    }

    @Override
    public double interestEarned() {
        double amount = sumTransactions();
        return amount * 0.001;
    }
}
