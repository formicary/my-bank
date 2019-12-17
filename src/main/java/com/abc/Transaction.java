package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public final double amount;

    public final Date transactionDate;

    public Transaction(final double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.now();
    }

}
