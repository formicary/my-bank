package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/** Class representing a bank customer. */
public class Customer {
    /** The constant counter. */
    private static int counter = 0;
    /** The customer\'s surname. */
    private String surname;
    /** The customer\'s first name. */
    private String firstName;
    /** List of the customer\'s accounts. */
    private List<Account> accounts;
    /** The ID of the customer. */
    private int id;

    /**
     * Instantiates a new Customer. Assigns it a unique ID.
     *
     * @param surname the customer\'s surname
     * @param firstName the customer\'s first name
     */
    public Customer(final String surname, final String firstName) {
        this.surname = surname;
        this.firstName = firstName;
        this.accounts = new ArrayList<>();
        this.id = ++counter;
    }

    /**
     * Add the parameter account to the customers list of accounts if it is not already present.
     *
     * @param account the account to add
     * @return the ID of the parameter account
     */
    public int addAccount(Account account) {
        if (!accounts.contains(account)) accounts.add(account);
        return account.getId();
    }

    /**
     * Open a new checking account.
     *
     * @return the id of the new account
     */
    public int openCheckingAccount() {
        return addAccount(new CheckingAccount());
    }

    /**
     * Open a new savings account.
     *
     * @return the id of the new account
     */
    public int openSavingsAccount() {
        return addAccount(new SavingsAccount());
    }

    /**
     * Open a new maxi savings account.
     *
     * @return the id of the new account
     */
    public int openMaxiSavingsAccount() {
        return addAccount(new MaxiSavingsAccount());
    }

    /**
     * Calculates the total interest earned across all the customer\'s accounts.
     *
     * @return the big decimal total interest earned by the customer
     */
    public BigDecimal totalInterestEarned() {
        BigDecimal total = BigDecimal.valueOf(0);
        for (Account account : accounts) total = total.add(account.interestEarned());
        return total;
    }

    /**
     * Concatenates and outputs the statements for all the customer\'s accounts, along with the
     * total balance across these accounts.
     *
     * @return all account statements as a single String
     */
    public String allStatements() {
        StringBuilder statement = new StringBuilder();
        if (accounts.isEmpty()) statement.append(getFullName()).append(" has no open accounts");
        else if (accounts.size() == 1)
            statement.append("Statement for ").append(getFullName()).append("\n");
        else statement.append("Statements for ").append(getFullName()).append("\'s accounts\n");
        double total = 0.0;
        for (Account account : accounts) {
            statement.append("\n").append(account.getStatement()).append("\n");
            total += account.getBalance();
        }
        statement.append("\nTotal In All Accounts ").append(String.format("$%,.2f", abs(total)));
        return statement.toString();
    }

    /**
     * Transfer money between two of the customer\'s accounts.
     *
     * @param sendingAccountId the id of the account to withdraw money from
     * @param receivingAccountId the id of the account to deposit money into
     * @param amount the amount to transfer
     * @throws NullPointerException if no account is found for either of the parameter account IDs
     */
    public void transferBetweenAccounts(
            int sendingAccountId, int receivingAccountId, double amount) {
        String noAccountFoundError = "customer " + id + " has not account with an ID of ";
        Account sendingAccount = getSingleAccount(sendingAccountId);
        if (sendingAccount == null) {
            throw new NullPointerException(noAccountFoundError + sendingAccountId);
        }
        Account receivingAccount = getSingleAccount(receivingAccountId);
        if (receivingAccount == null) {
            throw new NullPointerException(noAccountFoundError + receivingAccountId);
        }

        sendingAccount.withdraw(amount);
        receivingAccount.deposit(amount);
    }

    /**
     * Gets all the customer\'s accounts.
     *
     * @return list of the customer\'s accounts
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * Gets all the customer\'s accounts that are of the Account subclass specified in the
     * parameter.
     *
     * @param <T> a subclass of Account
     * @param accountType the Account subclass to filter results by
     * @return list of the customer\'s accounts that are of the type specified in the parameter
     */
    public <T extends Account> List<Account> getAccounts(Class<T> accountType) {
        List<Account> returnAccounts = new ArrayList<>();
        for (Account account : accounts) {
            if (account.getClass().equals(accountType)) {
                returnAccounts.add(account);
            }
        }
        return returnAccounts;
    }

    /**
     * Gets the account with the parameter ID if the customer has such an account.
     *
     * @param id the id of the account to return
     * @return the account with the parameter ID. Will be null is no such account is found
     */
    public Account getSingleAccount(int id) {
        Account returnAccount = null;
        for (Account account : accounts) {
            if (account.getId() == id) {
                returnAccount = account;
                break;
            }
        }
        return returnAccount;
    }

    /**
     * Gets the number of accounts that the customer has open.
     *
     * @return the number of accounts
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * Gets the customer\'s full name.
     *
     * @return the customer\'s first and surname separated by a space
     */
    public String getFullName() {
        return firstName + " " + surname;
    }

    /**
     * Gets the customer\'s surname.
     *
     * @return the customer\'s surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * Sets surname.
     *
     * @param surname the customer\'s surname
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * Gets the customer\'s first name.
     *
     * @return the customer\'s first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name.
     *
     * @param firstName the customer\'s first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets the customer\'s id.
     *
     * @return the customer\'s id
     */
    public int getId() {
        return id;
    }

    public String toString() {
        return "ID: " + id + ", Name: " + getFullName();
    }
}
