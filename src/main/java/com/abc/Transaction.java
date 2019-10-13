package com.abc;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Math.abs;

public class Transaction {
    public final BigDecimal amount;
    public final String transactionType;


    private Date transactionDate;

    public Transaction(BigDecimal amount, String transactionType) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
        this.transactionType = transactionType;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }

    public String toString(){
        return this.transactionDate + " " + this.transactionType + ": " + Conversion.toDollars(this.amount);
    }
}
