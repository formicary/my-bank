package com.abc;

import java.math.BigDecimal;
import org.joda.time.LocalDateTime;

public class Transaction {
    private final BigDecimal amount;

    private final LocalDateTime date;

    public Transaction(BigDecimal amount) {
        this.amount = amount;
        this.date = new LocalDateTime();
    }

    public BigDecimal getAmount() {
        return amount;
    }
    
    public LocalDateTime getDate() {
        return date;
    }
}
