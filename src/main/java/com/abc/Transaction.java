package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public final double amount;
    private Date transactionDate;
    public int numberOfDays;

    // number of milliseconds in a day is 8640000
    public static final long millisecondsInADay = 86400000;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    // method to find time since last transaction
    public int ageOfThisTransaction() {

        Date currentDate = DateProvider.getInstance().now();
        Long timeElapsed = currentDate.getTime() - transactionDate.getTime();

        // divide the number of milliseconds by this to get days
        numberOfDays = (int) (timeElapsed / millisecondsInADay);

        System.out.println("Time since last transaction: " + timeElapsed + "ms");
        System.out.println("Time since last transaction (Days): " + numberOfDays);
        return numberOfDays;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }
}
