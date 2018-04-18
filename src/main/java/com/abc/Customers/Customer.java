package com.abc.Customers;

import com.abc.Accounts.AccountType;
import com.abc.Accounts.IAccount;
import com.abc.Accounts.IAccountManager;
import com.abc.Utils.BankUtils;

import java.util.List;

/**
 * Represents a customer.
 */
public class Customer implements ICustomer {
    /**
     * The name of the customer.
     */
    private String name;

    /**
     * The customer ID.
     */
    private int customerId;

    /**
     * The account manager.
     */
    private IAccountManager accountManager;

    /**
     * Initializes a new instance of this class.
     *
     * @param accountManager The account manager.
     * @param name The name.
     * @param customerId The customer ID.
     */
    public Customer(IAccountManager accountManager, String name, int customerId) {
        this.accountManager = accountManager;
        this.name = name;
        this.customerId = customerId;
    }

    /**
     * Gets the name of the customer.
     *
     * @return The name of the customer.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the ID of the customer.
     *
     * @return The ID of the customer.
     */
    public int getCustomerId() {
        return this.customerId;
    }

    /**
     * Opens an account in the name of the given customer ID.
     *
     * @param accountType The account type.
     *
     * @returns The ID of the newly opened account.
     * @throws IllegalArgumentException Thrown when the given account type is not recognized.
     */
    public int openAccount(AccountType accountType) {
        return this.accountManager.openAccount(this.customerId, accountType);
    }

    /**
     * Gets the number of accounts opened by the customer.
     *
     * @return The number of accounts opened by the customer.
     */
    public int getNumberOfAccounts() {
        return this.accountManager.getNumberOfAccounts();
    }

    /**
     * Calculates the total interest earned across all accounts.
     *
     * @return The total interest earned across all accounts.
     */
    public double calculateTotalInterestEarned() {
        return this.accountManager.calculateTotalInterestEarned(this.customerId);
    }

    /**
     * Gets an overall statement for the costumer's accounts.
     *
     * @return The statement.
     */
    public String getStatement() {
        String statement = "Statement for " + name + "\n";
        double total = 0.0;

        List<IAccount> accounts = this.accountManager.getAccounts(this.customerId);

        for (IAccount account : accounts) {
            statement += "\n" + account.getAccountStatement() + "\n";
            total += account.sumTransactions();
        }

        statement += "\nTotal In All Accounts " + BankUtils.toDollars(total);

        return statement;
    }

    /**
     * Returns a string that represents this instance.
     *
     * @return The string that represents this instance.
     */
    @Override
    public String toString() {
        return String.format(
                "[Customer: name=%s, customerId=%s, accountManager=%s]", this.name, this.customerId, this.accountManager);
    }
}
