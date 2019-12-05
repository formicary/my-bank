package com.abc;

import java.util.Calendar;
import java.util.Date;
import java.math.BigDecimal;

public class Transaction {
    public BigDecimal amount;
    public Date transactionDate;

    public Transaction(double amount) {
        this.amount = new BigDecimal(amount);
        this.transactionDate = DateProvider.getInstance().now();
    }
}
