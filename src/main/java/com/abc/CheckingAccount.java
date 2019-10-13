package com.abc;

import java.math.BigDecimal;

public class CheckingAccount extends Account {
    public CheckingAccount() {
        super("Checking Account");
    }

    @Override
    long interestEarned() {
        // TODO: 10/10/2019 Remove magic numbers
        BigDecimal amount = getBalance();

//        return amount * 0.001;
        return 0;
    }
}
