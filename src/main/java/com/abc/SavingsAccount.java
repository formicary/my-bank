package com.abc;

public class SavingsAccount extends Account {
    public static double lIR = 0.001;
    public static double uIR = 0.002;
    public static double threshold = 1000;

    public SavingsAccount() {
        super(Accounts.SAVINGS);
    }

    public double interestEarned() {
        double amount = sumTransactions();
        if (amount <= 0) {
            return 0;
        }
        else if (amount <= threshold) {
            return amount * lIR;
        } else {
            return (threshold * lIR) + ((amount - threshold) * uIR);
        }
    }
}
