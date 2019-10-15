package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MaxiAccount extends Account {
    private static final BigDecimal RATE_1 = new BigDecimal("0.02");    //2%
    private static final BigDecimal RATE_2 = new BigDecimal("0.05");    //5%
    private static final BigDecimal RATE_3 = new BigDecimal("0.10");    //10%

    public MaxiAccount() {
        super("Maxi Saving Account");
    }

    @Override
    BigDecimal interestEarned() {
        BigDecimal balance = getBalance();
        BigDecimal result;

        if (balance.compareTo(PRIMARY_BALANCE) <= 0) {
            result = balance.multiply(RATE_1);
            result = result.setScale(2, RoundingMode.HALF_EVEN);
            return result;
        } else if (balance.compareTo(SECONDARY_BALANCE) <= 0) {
            result = balance.multiply(RATE_2);
            result = result.setScale(2, RoundingMode.HALF_EVEN);
            return result;
        } else {
            //balance > £2000
            result = balance.multiply(RATE_3);
            result = result.setScale(2, RoundingMode.HALF_EVEN);
            return result;
        }
    }
}
