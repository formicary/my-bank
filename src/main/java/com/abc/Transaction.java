package com.abc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class Transaction {
    final double amount; // todo should use BigDecimal?
    final String type;
    private final LocalDate transactionDate;

    Transaction(final double amount, final String type) {
        this.amount = amount;
        this.transactionDate = DateProvider.now();
        this.type = type;
    }
    LocalDate getTransactionDate(){
        return this.transactionDate;
    }
    String getTransactionType(){
        return this.type;
    }
    @Override
    public String toString(){
        return this.transactionDate + " " + this.type + ": " + BankUtils.toDollars(this.amount);
    }
}
