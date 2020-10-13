package com.abc;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Transaction {
    enum TransactionType {
        WITHDRAW("withdraw"),
        DEPOSIT("deposit");

        private String transactionTypeName;

        public String getTransactionTypeName() {
            return transactionTypeName;
        }

        TransactionType(String transactionTypeName) {
            this.transactionTypeName = transactionTypeName;
        }
    }

    private final double amount;
    private LocalDate transactionDate;
    private TransactionType transactionType;

    public Transaction(double amount, TransactionType transactionType) {
        this.amount = amount;
        this.transactionDate = LocalDate.now();
        this.transactionType = transactionType;
    }

    public double getTransactionAmount() {
        return amount;
    }

    public String getTransactionType() {
        return transactionType.getTransactionTypeName();
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public boolean isThereTenDaysWithdraw() {
        if (getTransactionType().equals("withdraw")) {
            return ChronoUnit.DAYS.between(transactionDate, LocalDate.now()) >= 10;
        } else
            return false;
    }

}
