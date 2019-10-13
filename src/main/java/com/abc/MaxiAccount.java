package com.abc;

import java.math.BigDecimal;

public class MaxiAccount extends Account {
    public MaxiAccount() {
        super("Maxi Saving Account");
    }

    @Override
    long interestEarned() {
        // TODO: 10/10/2019 Remove magic numbers
        BigDecimal amount = getBalance();
//        if (amount <= 1000)
//            return amount * 0.02;
//        if (amount <= 2000)
//            return 20 + (amount - 1000) * 0.05;
//        return 70 + (amount - 2000) * 0.1;
        return 0;
    }
}
