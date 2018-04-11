package com.abc;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

// As it wasn't specified that there should be an overdraft, there is extra validation on withdrawal
public abstract class Account {

    protected static final int DAILY_RATE_METHOD = 365;
    protected String accountName;
    protected List<Transaction> transactions;
    private double balance;

    public Account() {
        this.transactions = new ArrayList<>();
        this.balance = 0;
    }

    public String getAccountName() {
        return accountName;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    /**
     * Uses formula: principal * DAILY_RATE + current balance,
     * where principal is the accumulated interest+balance of previous day.
     * <p>
     * Compounds and accrues daily interest at the end of the day
     *
     * @return Gives interest until including last transaction
     */
    protected abstract double interestEarned();


    protected Map<Integer, Double> getFinalDayTransactionsMap() {
        return getFinalDayTransactions().stream()
                .collect(Collectors.toMap(Transaction::getDay, Transaction::getBalance));
    }


    private HashSet<Integer> getDays() {
        return transactions.stream().map(Transaction::getDay)
                .collect(Collectors.toCollection(HashSet::new));
    }

    /**
     * @return all transactions that are the last ones for their day
     */
    private ArrayList<Transaction> getFinalDayTransactions() {
        ArrayList<Transaction> finalDayTransactions = new ArrayList<>();
        HashSet<Integer> days = getDays();
        for (int day : days) {
            // last transaction of the day
            Transaction lastTransaction = transactions.stream().filter(t -> t.getDay() == day)
                    .max(Comparator.comparing(Transaction::getDate)).get();
            finalDayTransactions.add(lastTransaction);
        }
        return finalDayTransactions;
    }

    public void deposit(double amount, LocalDateTime time) {
        if (amount <= 0) {
            System.out.println("amount must be greater than zero");
        } else {
            balance += amount;
            transactions.add(new Transaction(amount, balance, time));
        }
    }

    public void withdraw(double amount, LocalDateTime time) {
        if (amount <= 0 || balance == 0) {
            System.out.println("Withdraw: amount and balance must be greater than zero");
            return;
        }
        if (balance - amount < 0) {
            System.out.println("Withdrawal must not be less than the balance");
            return;
        }
        balance -= amount;
        transactions.add(new Transaction(-amount, balance, time));

    }

    public double getBalance() {
        return balance;
    }
}
