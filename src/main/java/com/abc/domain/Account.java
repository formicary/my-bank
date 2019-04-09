package com.abc.domain;

import com.abc.accounts.AccountType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents an Account.
 */
public class Account {

    private final AccountType accountType;

    private final List<Transaction> transactions = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param accountType account type.
     */
    public Account(AccountType accountType) {
        this.accountType = accountType;
    }

    /**
     * @return the account type.
     */
    public AccountType getAccountType() {
        return accountType;
    }

    /**
     * @return a list of the transactions.
     */
    public List<Transaction> getTransactions() {
        return Collections.unmodifiableList(transactions);
    }

    /**
     * Decreses the balance of this account.
     * It registers a new account transaction.
     *
     * @param amount the amount of the withdrawal in Dollar.
     */
    public void withdraw(double amount) {
        validateAmount(amount);
        transactions.add(new Transaction(-amount, today()));
    }

    /**
     * Increases the balance of this account.
     * It registers a new account transaction.
     *
     * @param amount the amount of the deposit in Dollar.
     */
    public void deposit(double amount) {
        validateAmount(amount);
        transactions.add(new Transaction(amount, today()));
    }

    /**
     * Calculates the interest earned.
     * The calculation strategy depends on the type of the account.
     *
     * @return the earned interest in Dollar.
     */
    public double interestEarned() {
        double amount = getBalance();
        return accountType.interestEarned(getBalance(), transactions);
    }

    /**
     * Calculates the actual balance of this account.
     * It summarizes all transactions of this account.
     *
     * @return the balance in Dollar.
     */
    public double getBalance() {
        double amount = 0.0;
        for (Transaction t : transactions) {
            amount += t.getAmount();
        }
        return amount;
    }

    private void validateAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
    }

    private LocalDate today() {
        return LocalDate.now();
    }

}
