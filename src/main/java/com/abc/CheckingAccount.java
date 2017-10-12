package com.abc;

public class CheckingAccount extends Account {
    private static double ir = 0.001;

    public CheckingAccount() {
        super(Accounts.CHECKING);
    }
    public double interestEarned() {
        double amount = sumTransactions();
        if (amount <= 0) {
            return 0;
        } else {
            return amount * ir;
        }
    }
}
