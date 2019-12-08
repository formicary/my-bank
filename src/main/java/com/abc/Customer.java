package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * Class to handle the customer of a bank
 */

public class Customer {

    /**
     * Name of the customer
     */
    private String name;

    /**
     * Accounts of the customer
     */
    private List<Account> accounts;

    /**
     * Constructor of the customer class
     * @param name name of the customer
     */
    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    /**
     * Getter for the name of the customer
     * @return name of customer
     */
    public String getName() {
        return name;
    }

    /**
     * function to open an account for the customer
     * @param account account to open
     * @return the customer
     */
    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    /**
     * Function to get the number of accounts of a customer
     * @return number of accounts
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * Function to calculate the total interest earned from all the accounts
     * @return total interest earned from all the accounts
     */
    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts) {
            total += a.interestEarned();
        }
        return total;
    }

    /**
     * Function to get the statement of the customer
     * @return statement of the customer
     */
    public String getStatement() {
        String statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    /**
     * Function to get the statement for an account
     * @param account account to get the statement of
     * @return the statement of the account
     */
    private String statementForAccount(Account account) {
        String s = "";

        // Translate to pretty account type
        switch (account.getAccountType()) {
            case CHECKING:
                s += "Checking Account\n";
                break;
            case SAVINGS:
                s += "Savings Account\n";
                break;
            case MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }

        double total = 0.0;
        for (Transaction t : account.getTransactions()) {
            s += "  " + (t.getAmount() < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.getAmount()) + "\n";
            total += t.getAmount();
        }
        s += "Total " + toDollars(total);
        return s;
    }

    /**
     * Function to format an amount to be shown in dollars
     * @param d amount
     * @return formatted amount in dollars
     */
    private String toDollars(double d) {
        return String.format("$%,.2f", abs(d));
    }
}
