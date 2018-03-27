package com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 * A class representing a customer of the bank. Contains a name, a customer
 * number, and a list of accounts.
 * 
 * @author Filippos Zofakis
 */
public class Customer {
    private static int customerIndex = 1;

    private String firstName, lastName;
    private int customerNumber;
    private List<Account> accounts;

    /**
     * Constructor initialising a customer object with a specified name and an
     * empty account list.
     * 
     * @param firstName
     *            The first name of the customer.
     * @param lastName
     *            The last name of the customer.
     */
    public Customer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;

        // Assigning the current customer index to this customer upon
        // initialisation,
        // starting from 1.
        this.customerNumber = customerIndex;
        // Incrementing the customer number index.
        customerIndex++;

        this.accounts = new ArrayList<Account>();
    }

    /**
     * Returns the full customer name.
     * 
     * @return A String representing the first name and last name of the
     *         customer.
     */
    public String getName() {
        return firstName + " " + lastName;
    }

    /**
     * Returns the customer number (index).
     * 
     * @return An int representing the number of the customer.
     */
    public int getCustomerNumber() {
        return customerNumber;
    }

    /**
     * Adds the specified account to the customer account list.
     * 
     * @param account
     *            The new account to be added.
     * @return The accounts list of the customer.
     */
    public List<Account> openAccount(Account account) {
        accounts.add(account);

        return accounts;
    }

    /**
     * Returns the list of accounts of the customer.
     * 
     * @return A List of Account objects representing the accounts of the
     *         customer.
     */
    public List<Account> getAllAccounts() {
        return accounts;
    }

    /**
     * Returns the number of accounts of the customer.
     * 
     * @return An integer representing the number of accounts a customer owns.
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * Transfers an amount between the specified accounts.
     * 
     * @return A boolean representing the status of the transaction: true, if
     *         completed successfully.
     */
    public boolean transferBetweenAccounts(double amount, Account from, Account to) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid transfer amount! Please enter an amount greater than zero.");
        }

        if (amount <= from.getBalance()) {
            from.withdraw(amount);
            to.deposit(amount);
            System.out.println("Successfully completed transfer of: " + Bank.toDollars(amount) + ".");
            return true;
        }

        System.out.println("Insufficient account balance! Please deposit, before attempting to transfer.");
        return false;
    }

    /**
     * Returns the total interest earned across all accounts of the customer.
     * 
     * @return A double representing the total interest earned across all
     *         accounts.
     */
    public double totalInterestEarned() {
        double interestFromAllAccounts = 0;

        for (Account account : accounts) {
            interestFromAllAccounts += account.getTotalInterestEarned();
        }
        return interestFromAllAccounts;
    }

    /**
     * Returns the statement for all accounts of the customer.
     * 
     * @return A String containing information about all accounts of the
     *         customer.
     */
    public String getFullStatement() {
        String fullStatement = "Statement for " + getName() + ":\n";
        double total = 0;

        for (Account account : accounts) {
            fullStatement += "\n" + getStatementForAccount(account) + "\n";
            total += account.getBalance();
        }

        fullStatement += "\nTotal across all accounts: " + Bank.toDollars(total);

        return fullStatement;
    }

    /**
     * Returns the statement for a specified account of the customer.
     * 
     * @param account
     *            The account in question.
     * @return A String containing information about the specified account.
     */
    public String getStatementForAccount(Account account) {
        String accountStatement = "";

        // Translate to pretty account type.
        switch (account.getType()) {
        case CHECKING:
            accountStatement += "Checking account:\n";
            break;
        case SAVINGS:
            accountStatement += "Savings account:\n";
            break;
        case MAXI_SAVINGS:
            accountStatement += "Maxi-Savings account:\n";
            break;
        }

        // Now total up all the transactions.
        double accountTotal = 0;
        for (Transaction transaction : account.getTransactions()) {
            double transactionAmount = transaction.getAmount();
            accountStatement += "- " + (transactionAmount < 0 ? "withdrawal" : "deposit") + " "
                    + Bank.toDollars(transactionAmount) + "\n";
            accountTotal += transactionAmount;
        }

        double totalInterestEarned = account.getTotalInterestEarned();
        accountStatement += "Total interest accrued: " + Bank.toDollars(totalInterestEarned);
        accountTotal += totalInterestEarned;

        accountStatement += "\nAccount total: " + Bank.toDollars(accountTotal);

        return accountStatement;
    }
}
