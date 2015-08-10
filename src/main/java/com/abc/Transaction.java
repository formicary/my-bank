package com.abc;

import java.math.BigDecimal;
import java.util.Date;

public class Transaction {
    private final BigDecimal amount;
    private final Date transactionDate;

    public Transaction(BigDecimal amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.now();
    }
    
    public BigDecimal getAmount() {
    	return amount;
    }
    
    public Date getDate() {
    	return transactionDate;
    }
}
