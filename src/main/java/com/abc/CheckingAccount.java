package com.abc;

import java.util.Date;

import static java.lang.Math.pow;

public class CheckingAccount extends Account {

    public CheckingAccount() {
        super(CHECKING);
    }

    public double interestEarned() {

        double startAmount = 0;
        double totalInterest = 0;
        double amount = 0;
        double flatRate = 1 + 0.001;
        double newRate;

        Date startDate = transactions.get(0).transactionDate;

        for (int i = 1; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);
            int days = t.daysToDate(startDate);

            newRate = pow(flatRate, days);
            amount = startAmount * newRate;
            totalInterest += (amount - startAmount);

            startAmount = amount + t.amount;
            startDate = t.transactionDate;
        }

        int days = DateProvider.getInstance().daysToNow(startDate);

        newRate = pow(flatRate, days);
        amount = startAmount * newRate;
        totalInterest += (amount - startAmount);

        return totalInterest;
    }
}