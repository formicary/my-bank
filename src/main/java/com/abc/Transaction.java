package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public final double amount;

    //TODO: can be final and public, as this is just a POJO
    private Date transactionDate;

    public Transaction(final double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

}
