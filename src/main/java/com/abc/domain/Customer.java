package com.abc.domain;

import com.abc.exceptions.InsufficientFundsException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a Customer.
 */
public class Customer {

    private String name;
    private List<Account> accounts = new ArrayList<>();

    /**
     * Constructor.
     *
     * @param name The name of the customer.
     */
    public Customer(final String name) {
        this.name = name;
    }

    /**
     * @return Customer's name.
     */
    public String getName() {
        return name;
    }

    /**
     * @return Customer's accounts.
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * Opens a new account for this customer.
     *
     * @param account The new account
     */
    public void openAccount(final Account account) {
        Objects.requireNonNull(account, "The account must not be null!");
        accounts.add(account);
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * @return the total interest earned by the customer. It takes all accounts into account (no pun intended).
     */
    public double totalInterestEarned() {
        double total = 0;
        for (final Account a : accounts) {
            total += a.interestEarned();
        }
        return total;
    }

    /**
     * @return A short (one line) description of the customer.
     */
    public String getShortDescription() {
        final int numberOfAccounts = accounts.size();
        final String accountText = numberOfAccounts > 1 ? "accounts" : "account";
        return String.format("%s (%d %s)", getName(), numberOfAccounts, accountText);
    }

    /**
     * Transfers money from one account to the other of the customer.
     *
     * @param source the source account. It will be withdrawn.
     * @param target the target account. It will be deposited.
     * @param amount the amount of money (in Dollar) to transfer.
     * @throws InsufficientFundsException if the source accoun't doesn't have enough money.
     * @throws IllegalArgumentException   if the account is not belongs to the customer, or the amount is not positive.
     */
    public void transfer(Account source, Account target, double amount) throws InsufficientFundsException {
        checkHasAccount(source, "source");
        checkHasAccount(target, "target");
        validateAmount(amount);

        if (source.getBalance() < amount) {
            throw new InsufficientFundsException();
        }
        source.withdraw(amount);
        target.deposit(amount);
    }

    private void checkHasAccount(Account account, String name) {
        if (!accounts.contains(account)) {
            throw new IllegalArgumentException("The customer doesn't own the %s account.");
        }
    }

    private void validateAmount(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }
    }

}
