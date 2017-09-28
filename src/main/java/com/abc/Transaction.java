package com.abc;

import java.time.LocalDateTime;
import java.util.Optional;


public class Transaction {

    private final double amount;
    private LocalDateTime transactionDate;
    private Optional<Account> anotherAccount;

    public Transaction(double amount, LocalDateTime date) {
        this.amount = amount;
        this.transactionDate = date;
        anotherAccount = Optional.empty();
    }

    public Transaction(double amount, Account second, LocalDateTime now) {
        this.amount = amount;
        this.transactionDate = now;
        anotherAccount = Optional.of(second);
    }

    public Transaction(double amount) {
        this(amount, DateProvider.getInstance().now());
    }

    public double getAmount() {
        return amount;
    }

    public LocalDateTime getTransactionDate() {
        return transactionDate;
    }

    public Optional<Account> getAnotherAccount() {
        return anotherAccount;
    }
}