package com.abc;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Transaction {

    private final BigDecimal amount;
    private final LocalDate transactionDate;

    public Transaction(BigDecimal amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public boolean isWithdrawal() {
        return amount.compareTo(BigDecimal.ZERO) < 0;
    }

}
