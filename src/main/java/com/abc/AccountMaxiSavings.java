package com.abc;

import java.util.Map;

public class AccountMaxiSavings extends Account {
    // no withdrawals for 10 days
    private static final double NO_WITHDRAWALS_RATE = 0.005;
    private static final double DEFAULT_RATE = 0.001;
    private static final double DAILY_RATE_DEFAULT = DEFAULT_RATE / DAILY_RATE_METHOD;
    private static final double DAILY_RATE_NO_WITHDRAWAL = NO_WITHDRAWALS_RATE / DAILY_RATE_METHOD;


    public AccountMaxiSavings() {
        super();
        this.accountName = "Maxi Savings Account";
    }


    @Override
    protected double interestEarned() {
        Map<Integer, Double> finalDayTransactions = getFinalDayTransactionsMap();
        int firstDay = transactions.get(0).getDay();
        int forDays = transactions.get(transactions.size() - 1).getDay();

        // interest plus balance
        double principal = finalDayTransactions.get(firstDay);
        double balance = principal;
        int noWithdrawalsDays = 0;

        if (forDays == firstDay) {
            return 0;
        }

        for (int i = firstDay + 1; i <= forDays; i++) {
            if (finalDayTransactions.containsKey(i)) {
                balance = finalDayTransactions.get(i);
                principal = principal * DAILY_RATE_DEFAULT + balance;
                noWithdrawalsDays = 0;
                continue;
            }
            noWithdrawalsDays++;
            if (noWithdrawalsDays > 10) {
                principal = principal * DAILY_RATE_NO_WITHDRAWAL + balance;
            } else {
                principal = principal * DAILY_RATE_DEFAULT + balance;
            }
        }
        // return only interest
        return principal - balance;
    }
}
