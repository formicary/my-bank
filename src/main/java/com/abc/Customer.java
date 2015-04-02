package com.abc;

import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.abs;

/**
 * Class Customer Represents the instance of a bank customer
 * and have a name for the customer
 * and the accounts the customer have.
 * Also a customer have some actions which are
 * the opening of an account
 * the retrieval on interests earned from the accounts
 * and the retrieval on statement for the accounts
 */
public class Customer {

    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    /**
     * Get the name of the customer
     *
     * @return String name of customer
     */
    public String getName() {
        return name;
    }

    /**
     * Add am account to the accounts of the customer
     *
     * @param account The account
     * @return the customer instance
     */
    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    /**
     * Return number of customer accounts
     *
     * @return the number of the accounts
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * Calculates the interests for the customer accounts
     *
     * @return Total interests for all the customer accounts
     */
    public double getTotalInterestEarned() {
        double total = 0.0d;
        for (Account account : accounts)
            total += account.getInterestEarned();
        return total;
    }

    /**
     * Returns a statement with all the types of the account a customer has
     * every transaction for each account and the sum of
     *
     * @return statement for all the accounts of the customer
     */
    public String getStatement() {
        StringBuilder statement = new StringBuilder("Statement for ");
        statement.append(name);
        double balance = 0.0d;

        for (Account account : accounts) {
            statement.append("\n").append(statementForAccount(account));
            balance += account.getSumTransactions();
        }

        statement.append("\n\nTotal In All Accounts ").append(toDollars(balance));
        return statement.toString();
    }

    /**
     * Creates a statement for an account of the customer
     *
     * @param account the account for which the statement will be created
     * @return statement for the customers account
     */
    private String statementForAccount(Account account) {
        StringBuilder statementBuilder = new StringBuilder("\n");
        statementBuilder.append(account.getAccountType().getDescription()).append("\n");

        double total = 0.0d;
        for (Transaction transaction : account.getTransactions()) {
            statementBuilder.append("  ").append(transaction.getAmount() < 0 ? "withdrawal" : "deposit").append(" ").append(toDollars(transaction.getAmount())).append("\n");
            total += transaction.getAmount();
        }
        statementBuilder.append("Total ").append(toDollars(total));
        return statementBuilder.toString();
    }

    /**
     * Converts an amount to dollars two decimals
     *
     * @param d the amount that will be converted
     * @return representation of the amount into dollars
     */
    private String toDollars(double d) {
        return String.format("$%,.2f", abs(d));
    }


}
