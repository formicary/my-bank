package com.abc;

import java.util.Date;
import java.math.BigDecimal;

public class Transaction {
    public final BigDecimal amount;

    public Date transactionDate;
    
    public Transaction(BigDecimal amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

}
