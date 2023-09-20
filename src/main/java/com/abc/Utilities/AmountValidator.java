package com.abc.Utilities;

public class AmountValidator {
    public static void isNegativeAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount provided must be greater than zero");
        }
    }

    public static void canWithdraw(double balance, double amount) {
        if (balance < amount) {
            throw new IllegalStateException("Insufficient funds to withdraw");
        }
    }
}
