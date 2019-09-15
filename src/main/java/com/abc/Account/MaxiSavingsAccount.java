package com.abc.Account;

import com.abc.Money;;

public class MaxiSavingsAccount extends Account {

    private static final String ACCOUNT_NAME = "Maxi-Savings Account";

    public String getName() {
        return ACCOUNT_NAME;
    }

    public Money getTotalInterestEarned() {
        Money balance = getBalance();

        Money lowerInterest = calculateInterest(
                balance,
                new Money("0.02"),
                new Money("0"),
                new Money("1000")
        );
        Money middleInterest = calculateInterest(
                balance,
                new Money("0.05"),
                new Money("1000"),
                new Money("2000")
        );
        Money topInterest = calculateInterest(
                balance,
                new Money("0.1"),
                new Money("2000")
        );

        return lowerInterest.add(middleInterest).add(topInterest);
    }
}
