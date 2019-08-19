package com.abc.bank;

import com.abc.utility.DateProvider;

import java.util.Date;

public class MaxiSavingsAccount extends Account {
    public MaxiSavingsAccount() {
        super("Maxi Savings Account");
    }

    public double interestEarned() {
        Date tenDaysAgoDate = DateProvider.getInstance().beforeTenDays();
        Date mostRecentTransactionDate = transactions.get(transactions.size() - 1).getTransactionDate();

        if (mostRecentTransactionDate.before(tenDaysAgoDate))
            return accountValue * 0.05;
        else
            return  accountValue * 0.001;
    }
}
