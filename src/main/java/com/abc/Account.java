package com.abc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Account {

    protected String accountTypeName;
    private List<Transaction> transactions;
    private LocalDate dateOfLastInterestsEarned;

    abstract double interestEarned();

    public Account() {
        this.transactions = new ArrayList<Transaction>();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            addTransaction(new Transaction(amount, Transaction.TransactionType.DEPOSIT));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            addTransaction(new Transaction(-amount, Transaction.TransactionType.WITHDRAW));
        }
    }

    public double sumTransactions() {
        return transactions.stream().mapToDouble(Transaction::getTransactionAmount).sum();
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setDateOfLastInterestsEarned(LocalDate date) {
        this.dateOfLastInterestsEarned = date;
    }

    public LocalDate getDateOfLastInterestsEarned() {
        return dateOfLastInterestsEarned;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }

    public String getAccountTypeName() {
        return accountTypeName;
    }

    public double countAllTransactionsForOneDay(LocalDate date) {
        List<Transaction> transactions = getTransactions();
        return transactions.stream().filter(item -> date.equals(item.getTransactionDate())).collect(Collectors.summingDouble(Transaction::getTransactionAmount));
    }

}
