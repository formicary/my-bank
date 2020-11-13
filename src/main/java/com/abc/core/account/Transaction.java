package com.abc.core.account;

import lombok.Getter;

import java.util.Calendar;
import java.util.Date;

@Getter
public class Transaction {

    private final double amount;
    private final Date transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = Calendar.getInstance().getTime();
    }

}
