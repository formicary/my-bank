package com.abc;

import java.util.Map;

public class AccountChecking extends Account {
    private static final double DEFAULT_RATE = 0.001;
    private static final double DAILY_RATE = DEFAULT_RATE / DAILY_RATE_METHOD;

    public AccountChecking() {
        super();
        this.accountName = "Checking Account";
    }

    @Override
    public double interestEarned() {
        Map<Integer, Double> finalDayTransactions = getFinalDayTransactionsMap();

        int firstDay = transactions.get(0).getDay();
        int forDays = transactions.get(transactions.size() - 1).getDay();
        // interest plus balance
        double principal = finalDayTransactions.get(firstDay);
        double balance = principal;

        // first day does not have interest
        if (forDays == firstDay) {
            return 0;
        }

        for (int i = firstDay + 1; i <= forDays; i++) {
            if (finalDayTransactions.containsKey(i)) {
                balance = finalDayTransactions.get(i);
                principal = principal * DAILY_RATE + balance;
                continue;
            }
            principal = principal * DAILY_RATE + balance;
        }

        return principal - balance;
    }


}
