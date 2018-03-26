package com.abc;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class MaxiSavingsAccount extends Account {

    public static final double interestRateLongTerm = 0.05;
    public static final double interestRateShortTerm = 0.001;

    public MaxiSavingsAccount() {
        super();
        accountType = 2; // Maxi Savings Account
    }

    @Override
    public double calculateInterest(double diffInDays, double balance){
        LocalDateTime currentDate = DateProvider.now();

        for(Transaction transaction:transactions) {

            LocalDateTime transactionDate = transaction.getTransactionDate();
            boolean isWithdrawal = transaction.isWithdrawal();

            if (!isWithdrawal) {
                diffInDays = ChronoUnit.DAYS.between(transactionDate, currentDate);
                if (diffInDays > 10) {
                    return (balance * Math.pow((1 + interestRateLongTerm / 365), diffInDays)) - balance;
                }
            }
        }

        return (balance * Math.pow((1 + interestRateShortTerm / 365), diffInDays)) - balance;
    }

}