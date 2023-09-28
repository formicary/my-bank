package com.abc.utilities;

import java.math.BigDecimal;

/**
 * Validates quantities given
 */
public class AmountValidator {

    /**
     * Throws and exception if the quantity given is less than or equal to zero
     * @param amount quantity given
     */
    public static void isNegativeAmount(BigDecimal amount) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount provided must be greater than zero");
        };
    }

    /**
     * Throws an exception if the quantity given is greater than the balance
     * @param balance account balance
     * @param amount quantity given
     */
    public static void canWithdraw(BigDecimal balance, BigDecimal amount) {
        if (balance.compareTo(amount) == -1) {
            throw new IllegalStateException("Insufficient funds to withdraw");
        };
    }
}
