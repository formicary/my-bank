package com.abc;

import java.util.Calendar;
import java.util.Date;

import static java.lang.Math.abs;

public class Transaction {

    public final double amount;

    private Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    public String getType() {
        return (this.amount < 0 ? "withdrawal" : "deposit");
    }

    public String toString() {
        return this.getType() + " " + toDollars(this.amount);
    }

    public static String toDollars(double d) {
        return String.format("$%,.2f", abs(d));
    }

}
