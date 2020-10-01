package com.abc.entity.impl;

import com.abc.util.DateProvider;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Transaction class defines a single transaction made onto an account
 * @author aneesh
 */
public class Transaction {
    public final BigDecimal amount;

    private Date transactionDate;

    public Transaction(BigDecimal amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
    }

}
