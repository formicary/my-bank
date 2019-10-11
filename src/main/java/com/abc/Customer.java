package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * TASK
 * A customer can open an account
 * A customer can deposit / withdraw funds from an account
 * A customer can request a statement that shows transactions and totals for each of their accounts
 * <p>
 * Additional
 * A customer can transfer between their accounts
 */

/**
 * The Customer class represents a user of an artificial bank application.
 */
public class Customer {
    private String name;
    private List<Account> accounts; //can currently hold many duplicate accounts

    /**
     * Constructs a new Customer with the given name.
     *
     * @param name the Customers name
     */
    public Customer(String name) {
        // TODO: 10/10/2019 Name validation
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    /**
     * Returns the name of the Customer.
     *
     * @return the Customers name
     */
    public String getName() {
        return name;
    }

    /**
     * Adds a new account to list of accounts.
     *
     * @param account new account
     * @return the current Customer object
     */
    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    /**
     * Gets the amount of accounts currently opened by a Customer.
     *
     * @return number of accounts
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * Displays interest earned from all accounts.
     *
     * @return interest earned
     */
    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    /**
     * Displays Transactions from ALL accounts and the total balance.
     *
     * @return a list of transactions from all accounts
     */
    public String getStatement() {
        // TODO: 11/10/2019 Use a StringBuilder here
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    /**
     * Displays transactions of a GIVEN account and its final balance.
     *
     * @param a the account to query
     * @return a list of transactions from the given account
     */
    private String statementForAccount(Account a) {
        // TODO: 11/10/2019 rename parameter
        // TODO: 11/10/2019 Move to account
        String s = "";

        //Translate to pretty account type
        switch (a.getAccountType()) {
            case Account.CHECKING:
                s += "Checking Account\n";
                break;
            case Account.SAVINGS:
                s += "Savings Account\n";
                break;
            case Account.MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        s += "Total " + toDollars(total);
        return s;
    }

    /**
     * Formats the input amount to 2 decimal place.
     *
     * @param d the input representing the price
     * @return String prefixed with dollar symbol
     */
    private String toDollars(double d) {
        // TODO: 11/10/2019 Change parameter name
        return String.format("$%,.2f", abs(d));
    }
}
