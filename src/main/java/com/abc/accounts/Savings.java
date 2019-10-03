package com.abc.accounts;

import com.abc.Transaction;

import java.util.Calendar;
import java.util.Date;

public class Savings extends Account {

    protected double intRate2;

    public Savings() {
        super();
        this.intRate = 0.001;
        this.intRate2 = 0.002;
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

    public double calculateInterest(int numDays, double currBalance){
        double interest = 0.0;
        if (currBalance <= 1000)
            // 0.1% if balance below 1000
            interest = (numDays/365.0) * intRate * currBalance;
        else
            // Otherwise 0.1% on first 1000, then 0.2%
            interest = ((numDays/365.0) * intRate2 * (currBalance-1000)) + (numDays/365.0);

        return interest;
    }

    public String toString() {
        return "Savings Account";
    }
}
