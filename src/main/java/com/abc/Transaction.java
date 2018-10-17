package com.abc;

import com.abc.util.Money;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    private final Money amount;

    private final Date transactionDate;

    public Transaction(Money amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    public Money getAmount() {
        return amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }
}
