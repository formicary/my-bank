package com.abc;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class Transaction {
    private final BigDecimal amount;
    private final Date transactionDate;
    private TransactionType type;
    public Transaction(bigDecimal amount)
    {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
        this.type = amount.compareTo(BigDecimal.ZERO) > 0 ? TransactionType.deposit : TransactionType.withdraw; ;
    }

    public BigDecimal retrieveAmount()
    {
        return amount;
    }

    public Date retrieveTransactionDate()
    {
        return new Date(TransactionDate.getTime());
    }

    public TransactionType retrieveType()
    {
        return type;
    }
//    public Transaction(double amount, Date transactionDate, String type)
//    {
//        this.amount = amount;
//        this.transactionDate = transactionDate;
//        this.type = type;
//    }

}
