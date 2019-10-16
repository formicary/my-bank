package com.abc;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Math.abs;

public class Transaction {
    public final BigDecimal amount;
    public final AccountOperation transactionType;

    public enum AccountOperation {
        GETACCOUNTSUMMARY, WITHRAW, DEPOSIT, TRANSFERTO, TRANSFERFROM
    }

    private Date transactionDate;

    public Transaction(BigDecimal amount, AccountOperation transactionType) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
        this.transactionType = transactionType;
    }



    public String toString(){
        return this.transactionDate + " " + this.transactionType + ": " + Conversion.toDollars(this.amount);
    }
}
