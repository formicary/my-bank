package com.abc;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

public class Transaction {
    private final BigDecimal amount;
    private final Timestamp transactionDate;

    public Transaction(BigDecimal amount) {
        this.amount = amount;
        this.transactionDate = new Timestamp(System.currentTimeMillis());
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }
}
