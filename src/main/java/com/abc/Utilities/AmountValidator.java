package com.abc.Utilities;

import java.math.BigDecimal;

public class AmountValidator {
    public static void isNegativeAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount provided must be greater than zero");
        }
    }

    public static void canWithdraw(BigDecimal balance, BigDecimal amount) {
        if (balance.compareTo(amount) == -1) {
            throw new IllegalStateException("Insufficient funds to withdraw");
        }
    }
}
