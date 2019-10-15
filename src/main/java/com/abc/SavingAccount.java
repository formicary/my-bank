package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class SavingAccount extends Account {

    public SavingAccount() {
        super("Savings Account");
    }

    @Override
    BigDecimal interestEarned() {
        BigDecimal balance = getBalance();
        BigDecimal result;

        //if balance <= 1000
        if (balance.compareTo(PRIMARY_BALANCE) <= 0) {
            result = balance.multiply(INTEREST_F1);
            result = result.setScale(2, RoundingMode.HALF_EVEN);
            return result;
        } else {
            result = balance.multiply(INTEREST_F2);
            result = result.setScale(2, RoundingMode.HALF_EVEN);
            return result;
        }
    }
}
