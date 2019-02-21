package com.abc;

import java.util.Date;
import java.util.UUID;

public class MaxiSavingsAccount extends Account {
    public MaxiSavingsAccount(UUID accountID) {
        super(accountID);
    }

    public double interestEarned(double turnover) {
        double amount = super.sumTransactions();

        if (!isWithdrawal(10)) {
            return amount * 0.05 * turnover;
        } else
            return amount * 0.001 * turnover;
    }

    // Checks whether 1 or more withdrawals have been made the last N number of days
    private boolean isWithdrawal(int noDays) {
        boolean check = false;
        Date now = DateProvider.getInstance().now();
        long daysInMilliSec = noDays * 24 * 60 * 60 * 1000;

        for (Transaction t : transactions) {
            if ((t.amount < 0) && (now.getTime() - t.getTransactionDate().getTime() < daysInMilliSec)) {
                check = true;
            }
        }
        return check;
    }
}
