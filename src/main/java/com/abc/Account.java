package com.abc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class Account implements InterestInterface {
    protected List<Transaction> transactions;
    protected double totalAmount;
    protected double defaultInterestRate;

    public Account() {
        this.transactions = new ArrayList<Transaction>();
        totalAmount = 0;
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            totalAmount += amount;
            transactions.add(new Transaction(amount));
        }
    }

    public void withdraw(double amount) {
        if (amount <= 0 || amount > totalAmount) {
            throw new IllegalArgumentException("amount must be greater than zero and greater than totalAmount");
        } else {
            totalAmount -= amount;
            transactions.add(new Transaction(-amount));
        }
    }

    public void transferTo(double amount, Account to) {
        transfer(amount,this, to);
    }

    public void transferFrom(double amount, Account from) {
        transfer(amount, from, this);
    }

    private synchronized void transfer(double amount, Account from, Account to) {
        if (amount <= 0) throw new IllegalArgumentException("Amount to transfer must be positive");
        else if (amount > from.totalAmount) throw new IllegalArgumentException("To send the money, you need to have the money");
        else {
            from.totalAmount -= amount;
            to.totalAmount += amount;
            LocalDateTime now = DateProvider.getInstance().now();
            from.addTransaction(new Transaction(-amount,to, now));
            to.addTransaction(new Transaction(amount, from, now));
        }
    }

    protected abstract double interestEarned(double amount, LocalDate date);

    public double interestEarnedDaily() {
        Map<LocalDate,Double> groupedTransactions = groupTransactionByDay();
        double result = 0;
        for (LocalDate date: groupedTransactions.keySet()) {
            result += interestEarned(groupedTransactions.get(date) + result, date);
        }
        return result;
    }

    protected Map<LocalDate, Double> groupTransactionByDay() {
        Map<LocalDate,Double> groupedTransactions = new TreeMap<>();
        transactions.forEach(t -> {LocalDate date = t.getTransactionDate().toLocalDate();
            if (groupedTransactions.containsKey(date)) {
                groupedTransactions.put(date, groupedTransactions.get(date) + t.getAmount());
            } else {
                groupedTransactions.put(date,t.getAmount());
            }
        });
        return groupedTransactions;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    private void addTransaction(Transaction t) {
        transactions.add(t);
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public abstract String toString();
}
