package com.abc;

import java.util.Calendar;
import java.util.Date;

public class Transaction {
    public final double amount;
    public final Date transactionDate;
    public final TransctionType type;

    public enum TransctionType{
        WITHDRAWAL, DEPOSIT, TRANSFER, INTEREST
    }

    public Transaction(double amount, TransctionType transctionType, Date transactionDate) {
        this.type = transctionType;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }
}
