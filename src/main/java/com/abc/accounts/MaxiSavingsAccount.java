package com.abc.accounts;

import com.abc.DateProvider;
import com.abc.Transaction;

import java.util.Date;

public class MaxiSavingsAccount extends Account {

    private static double NO_RECENT_WITHDRAW_INTEREST_RATE = 0.05;
    private static double RECENT_WITHDRAW_INTEREST_RATE = 0.001;

    public MaxiSavingsAccount() {
        super();
    }

    public double interestEarned() {
        double amount = sumTransactions();
        if (hasRecentWithdrawals())
            return amount * RECENT_WITHDRAW_INTEREST_RATE;
        else
            return amount * NO_RECENT_WITHDRAW_INTEREST_RATE;
    }

    private boolean hasRecentWithdrawals() {
        Date tenDaysAgo = DateProvider.getInstance().earlierDate(10);
        for (Transaction t: getTransactions()) {
            if (t.amount < 0.0 && t.transactionDate.after(tenDaysAgo)) {
                return true;
            }
        }
        return false;
    }

    protected String getPrettyAccountType() {
        return "Maxi Savings Account\n";
    }


}
