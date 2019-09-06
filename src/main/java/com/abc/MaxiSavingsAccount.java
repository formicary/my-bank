package com.abc;

import java.util.Date;

import static java.lang.Math.pow;

public class MaxiSavingsAccount extends Account {

    public static final double HIGHRATE = 1.05;
    public static final double LOWRATE = 1.001;

    public static final int PRINCIPAL = 0;
    public static final int TOTALINTEREST = 1;
    public static final int LOWRATEDAYS = 2;

    public MaxiSavingsAccount() {
        super(MAXI_SAVINGS);
    }

    public double interestEarned() {

        Date startDate = transactions.get(0).transactionDate;
        double[] compoundResults = new double[] { 0, 0, 0 };

        for (int i = 1; i < transactions.size(); i++) {
            Transaction t = transactions.get(i);
            int daysBetweenTransaction = t.daysToDate(startDate);

            compoundResults = calculateInterest(daysBetweenTransaction, compoundResults);

            compoundResults[PRINCIPAL] += t.amount;
            startDate = t.transactionDate;

            if (t.amount < 0)
                compoundResults[LOWRATEDAYS] = 10;
        }

        int daysToNow = DateProvider.getInstance().daysToNow(startDate);
        compoundResults = calculateInterest(daysToNow, compoundResults);

        return compoundResults[TOTALINTEREST];
    }

    private double[] calculateInterest(int daysBetweenTransaction, double[] results) {

        double amount = 0;
        double startAmount = results[PRINCIPAL];
        double totalInterest = results[TOTALINTEREST];
        int lowRateDays = (int) results[LOWRATEDAYS];
        int highRateDays = 0;

        if (lowRateDays > 0) {
            if (daysBetweenTransaction < lowRateDays) {
                amount = compound(startAmount, LOWRATE, daysBetweenTransaction);
                lowRateDays -= daysBetweenTransaction;
            } else if (daysBetweenTransaction == lowRateDays) {
                amount = compound(startAmount, LOWRATE, 10);
                lowRateDays = 0;
            } else {
                amount = compound(startAmount, LOWRATE, lowRateDays);
                highRateDays = daysBetweenTransaction - lowRateDays;
                lowRateDays = 0;
            }
            totalInterest += (amount - startAmount);
            startAmount = amount;
        } else {
            highRateDays = daysBetweenTransaction;
        }

        if (highRateDays > 0) {
            amount = compound(startAmount, HIGHRATE, highRateDays);
            totalInterest += (amount - startAmount);
            startAmount = amount;
            highRateDays = 0;
        }

        results[PRINCIPAL] = startAmount;
        results[TOTALINTEREST] = totalInterest;
        results[LOWRATEDAYS] = lowRateDays;

        return results;
    }

    private double compound(double startAmount, double interest, int days) {
        double newRate = pow(interest, days);
        double amount = startAmount * newRate;

        return amount;
    }
}