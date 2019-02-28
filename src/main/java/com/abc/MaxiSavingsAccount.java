package com.abc;

import java.lang.Math;
import java.time.LocalDate;
import java.util.Iterator;

import static java.time.temporal.ChronoUnit.DAYS;

public class MaxiSavingsAccount extends Account {

    public static final double INTEREST_RATE_0 = 0.001;
    public static final double INTEREST_RATE_1 = 0.05;

    @Override
    public double currentBalance() {
        if (this.transactions.size() == 0) return 0.0;

        Iterator<Transaction> iter = this.transactions.iterator();
        Transaction tx = iter.next();

        double balance = 0.0;
        LocalDate lastWithdrawalDate = tx.date.minusDays(10);
        for (LocalDate date = tx.date; !date.isAfter(LocalDate.now()); date = date.plusDays(1)) {
            balance += dailyInterest(balance, DAYS.between(lastWithdrawalDate, date));

            while (date.isEqual(tx.date)) {
                balance += tx.amount;
                if (tx.amount < 0) {
                    lastWithdrawalDate = date;
                }
                if (iter.hasNext()) {
                    tx = iter.next();
                } else {
                    break;
                }
            }
        }

        return balance;
    }

    public String accountType() {
        return "Maxi Savings Account";
    }

    public double dailyInterest(double balance, long daysSinceLastWithdrawal) {
        double rate = daysSinceLastWithdrawal <= 10 ? INTEREST_RATE_0 : INTEREST_RATE_1;
        return balance * rate / 365;
    }

    public double dailyInterest(double balance) {
        return dailyInterest(balance, 0);
    }

}
