package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * Class to handle the customer of a bank
 */

class Customer {

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
    Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    /**
     * Getter for the name of the customer
     * @return name of customer
     */
    String getName() {
        return name;
    }

    /**
     * function to open an account for the customer
     * @param account account to open
     * @return the customer
     */
    Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    /**
     * Function to get the number of accounts of a customer
     * @return number of accounts
     */
    int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * Function to calculate the total interest earned from all the accounts
     * @return total interest earned from all the accounts
     */
    double totalInterestEarned() {
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
    String getStatement() {
        StringBuilder stringBuilder = new StringBuilder("Statement for " + name + "\n");
        double total = 0.0;
        for (Account a : accounts) {
            stringBuilder.append("\n" + statementForAccount(a) + "\n");
            total += a.sumTransactions();
        }
        stringBuilder.append("\nTotal In All Accounts " + toDollars(total));
        return stringBuilder.toString();
    }

    /**
     * Function to get the statement for an account
     * @param account account to get the statement of
     * @return the statement of the account
     */
    private String statementForAccount(Account account) {
        StringBuilder stringBuilder = new StringBuilder();

        // Translate to pretty account type
        switch (account.getAccountType()) {
            case CHECKING:
                stringBuilder.append("Checking Account\n");
                break;
            case SAVINGS:
                stringBuilder.append("Savings Account\n");
                break;
            case MAXI_SAVINGS:
                stringBuilder.append("Maxi Savings Account\n");
                break;
        }

        double total = 0.0;
        for (Transaction t : account.getTransactions()) {
            stringBuilder.append("  " + (t.getAmount() < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.getAmount()) + "\n");
            total += t.getAmount();
        }
        stringBuilder.append("Total " + toDollars(total));
        return stringBuilder.toString();
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
