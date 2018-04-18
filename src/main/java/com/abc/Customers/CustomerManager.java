package com.abc.Customers;

import com.abc.Utils.BankUtils;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a customer manager.
 */
public class CustomerManager implements ICustomerManager {
    /**
     * The customer ID manager.
     */
    private ICustomerIdManager customerIdManager;

    /**
     * The customer factory
     */
    private ICustomerFactory customerFactory;

    /**
     * The customers of this bank.
     */
    private List<ICustomer> customers;

    /**
     * Initializes a  new instance of this class;
     *
     * @param customerIdManager The customer ID manager.
     * @param customerFactory The customer factory.
     */
    public CustomerManager(ICustomerIdManager customerIdManager, ICustomerFactory customerFactory) {
        this.customerIdManager = customerIdManager;
        this.customerFactory = customerFactory;
        this.customers = new LinkedList<ICustomer>();
    }

    /**
     * Adds a new customer.
     *
     * @param name The name of the customer.
     *
     * @return The ID of the newly added customer.
     */
    public int addCustomer(String name) {
        int customerId = this.customerIdManager.generateCustomerId();

        customers.add(this.customerFactory.createCustomer(name, customerId));

        return customerId;
    }

    /**
     * Gets the customer that corresponds to the given customer ID.
     *
     * @param customerId The customer ID.
     *
     * @return The customer.
     */
    public ICustomer getCustomer(int customerId) {
        for (ICustomer customer : this.customers) {
            if (customer.getCustomerId() == customerId) {
                return customer;
            }
        }

        return null;
    }

    /**
     * Gets the customer statement for the given customer ID.
     *
     * @param customerId The customer ID.
     *
     * @return The customer's bank statement.
     * @throws CustomerException Thrown when no customer exists for the given customer ID.
     */
    public String getCustomerStatement(int customerId) {
        ICustomer customer = this.getCustomer(customerId);

        if (customer == null) {
            throw new CustomerException(
                    String.format("Failed to get customer statement as no customer exists for given ID %s", customerId));
        }

        return customer.getStatement();
    }

    /**
     * Gets a summary of all known customers.
     *
     * @return The summary of all known customers.
     */
    public String getCustomerSummary() {
        String summary = "Customer Summary";

        for (ICustomer c : customers) {
            summary += "\n - " + c.getName() + " (" + BankUtils.formatSentence(c.getNumberOfAccounts(), "account") + ")";
        }

        return summary;
    }

    /**
     * Calculates the total amount of interest paid.
     *
     * @return The total amount of interest paid.
     */
    public double calculateTotalInterestPaid() {
        double total = 0;

        for(ICustomer c: customers) {
            total += c.calculateTotalInterestEarned();
        }

        return total;
    }

    /**
     * Returns a string that represents this instance.
     *
     * @return The string that represents this instance.
     */
    @Override
    public String toString() {
        return String.format(
                "[CustomerManager: customerIdManager=%s, customerFactory=%s, customer=%s]", this.customerIdManager, this.customerFactory, this.customers);
    }
}
