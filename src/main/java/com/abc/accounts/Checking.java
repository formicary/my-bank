package com.abc.accounts;

import com.abc.Transaction;

import java.util.Calendar;
import java.util.Date;

public class Checking extends Account {

    public Checking() {
        super();
        this.intRate = 0.001;
    }

    // Calculate total interest earned on the account from first transaction till now
    // Daily accrual and compounding of interest
    public double interestEarned() {
        // If no transactions just return 0
        if (transactions.size() == 0)
            return 0;

        double totalEarnedInterest = 0;
        Transaction prevTransaction = transactions.get(0);
        double currBalance = prevTransaction.getAmount();
        double earnedInterest = 0.0;

        // Assuming transactions are in chronological order
        for (int i = 1; i < transactions.size(); i++) {
            Transaction currTransaction = transactions.get(i);
            int numDays = daysBetween(prevTransaction.getTransactionDate(), currTransaction.getTransactionDate());

            // Get accrued interest
            earnedInterest = calculateInterest(numDays, currBalance);

            // Update balance and total interest
            currBalance += earnedInterest;
            currBalance += currTransaction.getAmount();
            totalEarnedInterest += earnedInterest;

            // Update prevTransaction
            prevTransaction = currTransaction;
        }

        // Get interest since last transaction till now
        Date now = Calendar.getInstance().getTime();
        int numDays = daysBetween(prevTransaction.getTransactionDate(), now);
        earnedInterest = calculateInterest(numDays, currBalance);
        totalEarnedInterest += earnedInterest;

        return totalEarnedInterest;
    }

    public double calculateInterest(int numDays, double currBalance) {
        double interest = (numDays/365.0) * intRate * currBalance;
        return interest;
    }

    public String toString() {
        return "Checking Account";
    }
}
