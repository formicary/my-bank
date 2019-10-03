package com.abc.accounts;

import com.abc.Transaction;

import java.util.Calendar;
import java.util.Date;

public class MaxiSavings extends Account {

    protected double intRate2;

    public MaxiSavings() {
        super();
        this.intRate = 0.05;
        this.intRate2 = 0.001;
    }

    // Calculate total interest earned on the account from first transaction till now
    // Daily accrual and compounding of interest
    public double interestEarned() {
        double totalEarnedInterest = 0;
        Transaction prevTransaction = transactions.get(0);
        double currBalance = prevTransaction.getAmount();
        double earnedInterest = 0.0;
        int daysSinceLastWithdrawal = 0;

        // Assuming transactions are in chronological order (for now)
        for (int i = 1; i < transactions.size(); i++) {
            Transaction currTransaction = transactions.get(i);
            int numDays = daysBetween(prevTransaction.getTransactionDate(), currTransaction.getTransactionDate());

            // Get accrued interest
            earnedInterest = calculateInterest(numDays, currBalance, daysSinceLastWithdrawal);

            // Update balance and total interest
            currBalance += earnedInterest;
            currBalance += currTransaction.getAmount();
            totalEarnedInterest += earnedInterest;

            // Update prevTransaction
            prevTransaction = currTransaction;

            // Update days since last withdrawal
            if (prevTransaction.getAmount() < 0)
                daysSinceLastWithdrawal = 0;
            else
                daysSinceLastWithdrawal += numDays;
        }

        // Get interest since last transaction till now
        Date now = Calendar.getInstance().getTime();
        int numDays = daysBetween(prevTransaction.getTransactionDate(), now);
        earnedInterest = calculateInterest(numDays, currBalance, daysSinceLastWithdrawal);
        totalEarnedInterest += earnedInterest;

        return totalEarnedInterest;
    }

    public double calculateInterest(int numDays, double currBalance, int daysSinceLastWithdrawal) {
        double interest = 0.0;
        if (daysSinceLastWithdrawal >= 10) {
            // If more than 10 days, just use highest rate
            interest = (numDays/365.0) * intRate * currBalance;
        } else if (daysSinceLastWithdrawal + numDays >= 10) {
            // If not yet more than 10 days, but will become more than 10 days
            int remainingDays = 10 - daysSinceLastWithdrawal; // See how many days at lower rate
            int bonusDays = numDays - remainingDays; // See how many days at higher rate

            interest += (remainingDays/365.0) * intRate2 * currBalance;
            currBalance += interest; // Update balance after lower rate days
            interest += (bonusDays/365.0) * intRate * currBalance;
        } else if (daysSinceLastWithdrawal + numDays < 10) {
            // If will not reach more than 10 days since last withdrawal
            interest = (numDays/365.0) * intRate2 * currBalance;
        }

        return interest;
    }

    public String toString() {
        return "Maxi Savings Account";
    }
}
