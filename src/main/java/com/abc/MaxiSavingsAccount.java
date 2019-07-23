package com.abc;

public class MaxiSavingsAccount extends Account {

    public MaxiSavingsAccount() {
        super();
    }

    @Override
    public double getInterestEarned() {

        double amount = sumTransactions();

        if (amount <= 1000)
            return amount * 0.02;
        if (amount <= 2000)
            if (isLastWithdrawInLastTenDays(transactions)) {
                return 20 + (amount - 1000) * 0.001;
            } else {
                return 20 + (amount - 1000) * 0.05;
            }
        return 70 + (amount - 2000) * 0.1;
    }
}
