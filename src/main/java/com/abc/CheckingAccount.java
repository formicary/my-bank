package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CheckingAccount extends Account {

    public CheckingAccount() {
        super("Checking Account");
    }

    @Override
    BigDecimal interestEarned() {
        BigDecimal balance = getBalance();
        BigDecimal result = balance.multiply(INTEREST_F1);
        result = result.setScale(2, RoundingMode.HALF_EVEN);
//        return amount * 0.001;
        return result;
    }
}
