package com.abc.accounts;

import com.abc.Transaction;
import com.abc.util.DateProvider;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.abc.util.CurrencyFormatter.toDollars;
import static java.time.temporal.ChronoUnit.DAYS;

public abstract class Account {

    List<Transaction> transactions;
    double interestRate;
    double accrueRate;
    double balance;
    LocalDateTime dateOfLastUpdate;

    public Account() {
        this.transactions = new ArrayList<>();
        interestRate = 0.0;
        balance = 0.0;
        accrueRate = 0.0;
        dateOfLastUpdate = DateProvider.getInstance().now();
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            updateAccount(new Transaction(amount));
        }
    }
    public void deposit(double amount, LocalDateTime date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            updateAccount(new Transaction(amount,date));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            updateAccount(new Transaction(-amount));
        }
    }
    public void withdraw(double amount, LocalDateTime date) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            updateAccount(new Transaction(-amount,date));
        }
    }

    private void updateAccount(Transaction transaction) {

        int daysSinceLastUpdate = (int) DAYS.between(dateOfLastUpdate, transaction.getTransactionDate());
        while (daysSinceLastUpdate>0){
            accrueInterest();
            compoundInterest();
            daysSinceLastUpdate--;
        }
        transactions.add(transaction);
        balance += transaction.getAmount();
        dateOfLastUpdate = transaction.getTransactionDate();
    }

    protected abstract void compoundInterest();

    protected abstract void accrueInterest();

    public double interestEarned(){

        return balance - sumTransactions();
    }

    public double sumTransactions() {
        return transactions.stream().mapToDouble(Transaction::getAmount).sum();
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public String getStatementInDollars(){

        StringBuilder s = new StringBuilder(this.toString());
        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : transactions) {
            s.append("  ").append(t.getStatementInDollars()).append("\n");
            total += t.getAmount();
        }
        s.append("Total ").append(toDollars(total));
        return s.toString();
    }

    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
}
