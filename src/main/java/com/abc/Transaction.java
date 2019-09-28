package com.abc;

import com.abc.util.DateProvider;

import java.time.LocalDateTime;

import static com.abc.util.StringFormatter.toDollars;

public class Transaction {

    private final double amount;
    private static final int WITHDRAWAL = 0;
    private static final int DEPOSIT = 1;
    private final int transactionType;
    private LocalDateTime transactionDate;

    public Transaction(double amount) {
        this.amount = amount;
        this.transactionDate = DateProvider.getInstance().now();
        transactionType = (amount < 0) ? WITHDRAWAL : DEPOSIT;
    }

    public Transaction(double amount, LocalDateTime date) {
        this(amount);
        transactionDate = date;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public double getAmount() {
        return amount;
    }

    public String getStatementInDollars(){
        return (transactionType == WITHDRAWAL ? "Withdrawal " : "Deposit ") + toDollars(amount);
    }

}
