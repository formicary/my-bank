package com.abc;

import com.abc.Accounts.*;
import com.abc.Customers.*;
import com.abc.Utils.DateProvider;

/**
 * Represents a bank.
 */
public class Bank {
    /**
     * The account factory.
     */
    private IAccountFactory accountFactory;

    /**
     * The account manager.
     */
    private IAccountManager accountManager;

    /**
     * The customer factory.
     */
    private ICustomerFactory customerFactory;

    /**
     * The customer manager.
     */
    private ICustomerManager customerManager;

    /**
     * Initializes a new instance of the Bank class.
     */
    public Bank() {
        this.accountFactory = new AccountFactory(DateProvider.getInstance());
        this.accountManager = new AccountManager(AccountIdManager.getInstance(), this.accountFactory);

        this.customerFactory = new CustomerFactory(this.accountManager);
        this.customerManager = new CustomerManager(CustomerIdManager.getInstance(), this.customerFactory);
    }

    /**
     * Registers a new customer with this bank.
     *
     * @param name The name of the customer.
     *
     * @return The ID of the newly registered customer.
     */
    public int registerCustomer(String name) {
        return this.customerManager.addCustomer(name);
    }

    /**
     * Opens an account in the name of the given customer ID.
     *
     * @param customerId The customer ID.
     * @param accountType The account type.
     *
     * @return The ID of the newly opened account
     */
    public int openAccount(int customerId, AccountType accountType) {
        return this.accountManager.openAccount(customerId, accountType);
    }

    /**
     * Deposits the given amount into a customer's account.
     *
     * @param customerId The customer ID.
     * @param accountId The account ID.
     * @param amount The amount of money to be deposited.
     */
    public void deposit(int customerId, int accountId, double amount) {
        this.accountManager.deposit(customerId, accountId, amount);
    }

    /**
     * Withdraws the given amount from a customer's account.
     *
     * @param customerId The customer ID.
     * @param accountId The account ID.
     * @param amount The amount of money to be withdrawn.
     */
    public void withdraw(int customerId, int accountId, double amount) {
        this.accountManager.withdraw(customerId, accountId, amount);
    }

    /**
     * Transfers the given amount from the source account ID to the destination account ID.
     *
     * @param customerId The customer ID.
     * @param sourceAccountId The ID of the source account.
     * @param destinationAccountId The ID of the destination account.
     * @param amount The amount of money to transfer.
     */
    public void transfer(int customerId, int sourceAccountId, int destinationAccountId, double amount) {
        this.accountManager.transfer(customerId, sourceAccountId, destinationAccountId, amount);
    }

    /**
     * Gets the customer statement for the given customer ID.
     *
     * @param customerId The customer ID.
     *
     * @return The customer's bank statement.
     */
    public String getCustomerStatement(int customerId) {
        return this.customerManager.getCustomerStatement(customerId);
    }

    /**
     * Gets a summary of the bank's customers.
     *
     * @return The summary of the bank's customers.
     */
    public String getCustomerSummary() {
        return this.customerManager.getCustomerSummary();
    }

    /**
     * Gets the total amount of interest paid.
     *
     * @return The total amount of interest paid.
     */
    public double getTotalInterestPaid() {
        return this.customerManager.calculateTotalInterestPaid();
    }
}
