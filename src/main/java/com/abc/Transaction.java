package com.abc;

import java.util.Date;

public class Transaction {
    public static final int DEPOSIT = 0;
    public static final int WITHDRAWAL = 1;

    public final double amount;
    public final Date transactionDate;
    public final int type;

    public Transaction(double amount, int type) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
        this.type = type;
    }
}
