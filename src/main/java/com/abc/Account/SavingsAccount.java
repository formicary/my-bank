package com.abc.Account;

import com.abc.Money;

public class SavingsAccount extends Account {

    private static final String ACCOUNT_NAME = "Savings Account";

    public String getName() {
        return ACCOUNT_NAME;
    }

    public Money getTotalInterestEarned() {
        Money balance = getBalance();

        Money lowInterest = calculateInterest(
                balance,
                new Money("0.001"),
                new Money("0"),
                new Money("1000")
        );
        Money topInterest = calculateInterest(
                balance,
                new Money("0.002"),
                new Money("1000")
        );

        return lowInterest.add(topInterest);
    }
}
