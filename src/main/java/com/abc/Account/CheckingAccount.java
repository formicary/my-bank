package com.abc.Account;

import com.abc.Money;;

public class CheckingAccount extends Account {

    private static final String ACCOUNT_NAME = "Checking Account";

    public String getName(){
        return ACCOUNT_NAME;
    }

    public Money getTotalInterestEarned() {
        Money balance = getBalance();

        return calculateInterest(
                balance,
                new Money("0.001"),
                new Money("0")
        );
    }

}
