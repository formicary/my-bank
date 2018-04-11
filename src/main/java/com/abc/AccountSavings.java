package com.abc;

import java.util.Map;

public class AccountSavings extends Account {

    private static final double FIRST_THOUSAND_RATE = 0.001;
    private static final double AFTER_THOUSAND_RATE = 0.002;
    private static final double DAILY_RATE_FIRST = FIRST_THOUSAND_RATE / DAILY_RATE_METHOD;
    private static final double DAILY_RATE_AFTER = AFTER_THOUSAND_RATE / DAILY_RATE_METHOD;


    public AccountSavings() {
        super();
        this.accountName = "Savings Account";
    }


    private double dailyInterest(double principal, double balance) {
        if (principal <= 1000)
            return principal * DAILY_RATE_FIRST + balance;
        else
            return (1000 * DAILY_RATE_FIRST + ((principal - 1000) * DAILY_RATE_AFTER)) + balance;
    }

    @Override
    protected double interestEarned() {
        Map<Integer, Double> finalDayTransactions = getFinalDayTransactionsMap();

        int firstDay = transactions.get(0).getDay();
        int forDays = transactions.get(transactions.size() - 1).getDay();
        // 1st - initial balance -> after - interest plus balance
        double principal = finalDayTransactions.get(firstDay);
        double balance = principal;

        if (forDays == firstDay) {
            return 0;
        }

        for (int i = firstDay + 1; i <= forDays; i++) {
            if (finalDayTransactions.containsKey(i)) {
                balance = finalDayTransactions.get(i);
                principal = dailyInterest(principal, balance);
                continue;
            }
            principal = dailyInterest(principal, balance);
        }

        return principal - balance;
    }

}
