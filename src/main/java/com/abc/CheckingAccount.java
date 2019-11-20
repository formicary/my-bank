package com.abc;

public class CheckingAccount extends Account {

    CheckingAccount() {
        super(AccountType.CHECKING);
    }

    @Override
    double calcInterest() {
        return getBalance() * 0.001;
    }

    @Override
    String genStatement() {
        return genStatement("Checking Account");
    }
}
