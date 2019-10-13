package com.ds.mo;

import java.math.BigDecimal;
import java.math.BigInteger;

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
