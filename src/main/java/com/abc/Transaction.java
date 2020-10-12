package com.abc;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

public class Transaction {
    private final double amount;
    private LocalDate transactionDate;
    private String transactionType;

    public Transaction(double amount, String transactionType) {
        this.amount = amount;
        this.transactionDate = LocalDate.now();
        this.transactionType=transactionType;
    }

    public double getTransactionAmount()
    {
        return amount;
    }

    public String getTransactionType()
    {
        return transactionType;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public boolean isThereTenDaysWithdraw()
    {
        if(transactionType.equals("withdraw")) {
            return ChronoUnit.DAYS.between(transactionDate, LocalDate.now()) >= 10;
        }
        else
            return false;
    }
}
