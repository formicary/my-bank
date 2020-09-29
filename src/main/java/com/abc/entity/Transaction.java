package com.abc.entity;

import com.abc.util.DateProvider;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
    public final BigDecimal amount;

    private Date transactionDate;

    public Transaction(BigDecimal amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

}
