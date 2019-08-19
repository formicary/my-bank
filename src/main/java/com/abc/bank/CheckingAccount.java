package com.abc.bank;

import com.abc.bank.Account;

public class CheckingAccount extends Account {
    public CheckingAccount() {
        super("Checking Account");
    }

    public double interestEarned() {
        return accountValue * 0.001;
    }
}
