package com.abc.classes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Transaction {
    private final double amount;

    private String transactionDate;
    private LocalDateTime unformattedTransactionDate;
    private String transactionType;

    public Transaction(double amount, String transactionType) {
        this.transactionDate = createTimeStamp();
        this.amount = amount;
        this.transactionType = transactionType;
    }

    // Getters//
    public String getTransactionDate() {
        return transactionDate;
    }

    public LocalDateTime getUnformattedTransactionDate() {
        return unformattedTransactionDate;
    }

    public double getTransactionAmount() {
        return amount;
    }

    public String getTransactionType() {
        return transactionType;
    }

    // Timestamp Function//
    public String createTimeStamp() {
        LocalDateTime localDateTime = LocalDateTime.now();
        unformattedTransactionDate = localDateTime;

        // Format to be more readable
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = formatter.format(localDateTime);

        return formattedDateTime;
    }

    // ADDITIONAL FEATURE
    public static boolean transactionDateOverTenDays(Transaction transaction) {
        boolean overTenDays = false;

        LocalDateTime dateToCompare = transaction.getUnformattedTransactionDate();

        // Get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();

        // Calculate the difference in days between the two dates
        long daysDifference = Math.abs(ChronoUnit.DAYS.between(dateToCompare, currentDateTime));

        // Check if the difference is greater than 10 days
        if (daysDifference >= 10) {
            overTenDays = true;
            return overTenDays;
        } else {
            return overTenDays;
        }

    }

    // Used only for debugging/testing purposes, forces the transaction date to over
    // 10 days to test interest rate change for MAXI_ACCOUNT
    public void forceTransactionDateOverTenDays() {
        unformattedTransactionDate = unformattedTransactionDate.plusDays(11);
    }

}
