package com.abc;

import com.abc.account.Account;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.abs;

/** The class {@code Customer} represents a Customer. */
public class Customer {

    private final String name;
    private final Map<String, Account> accounts = new HashMap<>();

    /**
     * Initalise a {@code Customer} with a given name.
     *
     * @param name The customer name
     */
    public Customer(final String name) {
        this.name = name;
    }

    /**
     * Get the customer name.
     *
     * @return The customer name
     */
    public String getName() {
        return name;
    }

    /**
     * Open an account.
     *
     * @param account The account that is to be opened
     */
    public void openAccount(final String id, final Account account) {
        accounts.put(id, account);
    }

    /**
     * Get the number of accounts.
     *
     * @return The number of accounts.
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * Get the total interest paid by the customer's accounts.
     *
     * @return The total interest paid
     */
    public BigDecimal getTotalInterestEarned() {
        return accounts
                .values()
                .stream()
                .map(Account::getInterestEarned)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * Get the customer's statement.
     *
     * @return The customer's statement
     */
    public String getStatement() {
        String statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (final Account account : accounts.values()) {
            statement += getAccountStatementString(account);
            total += account.getTransactionsSum().doubleValue();
        }
        statement += getAccountTotalString(total);
        return statement;
    }

    private String getAccountStatementString(final Account account) {
        return "\n" + account.getStatement() + "\n";
    }

    private String getAccountTotalString(final double total) {
        return "\nTotal In All Accounts " + toDollars(total);
    }

    private String toDollars(final double d) {
        return String.format("$%,.2f", abs(d));
    }

    /**
     * Get the customer's accounts summary.
     *
     * @return The customer's accounts summary
     */
    public String getSummary() {
        return name + " (" + format(getNumberOfAccounts(), "account") + ")";
    }

    private String format(final int number, final String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    /**
     * Deposit to the customer's account with the given id.
     *
     * @param id The id of the account to deposit to
     * @param amount The amount to deposit
     */
    public void depositToAccount(final String id, final BigDecimal amount) {
        getAccount(id).deposit(amount);
    }

    private Account getAccount(final String id) {
        return accounts.get(id);
    }

    /**
     * Withdraw from the customer's account with the given id.
     *
     * @param id The id of the account to deposit to
     * @param amount The amount to withdraw
     */
    public void withdrawFromAccount(final String id, final BigDecimal amount) {
        getAccount(id).withdraw(amount);
    }

    /**
     * Transfer the given amount between accounts.
     *
     * @param fromAccountId The from account id
     * @param toAccountId The to account id
     * @param amount The amount to transfer
     */
    public void transferBetweenAccounts(
            final String fromAccountId, final String toAccountId, final BigDecimal amount) {
        final Account fromAccount = getAccount(fromAccountId);
        final Account toAccount = getAccount(toAccountId);
        fromAccount.transferTo(toAccount, amount);
    }
}