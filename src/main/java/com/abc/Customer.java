package com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 * A {@link Customer} represents a customer of the {@link Bank}.
 */
public class Customer {
    private String name;
    private List<Account> accounts;
    
    /**
     * Constructs a {@link Customer}.
     * @param name The name of the customer.
     */
    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }
    
    /**
     * Gets the name of the {@link Customer}.
     * @return See above.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Opens the specified {@link Account} for this {@link Customer}.
     * @param account The account to open.
     * @return This {@link Customer}.
     */
    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    /**
     * Returns the number of accounts the {@link Customer} has open.
     * @return See above.
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }
    
    /**
     * Calculates the total interest for the {@link Customer} across all of the {@link Account}'s.
     * @return Total Interest.
     */
    public double totalInterestEarned() {
        double total = 0;
        for (Account account : accounts) {
            total += account.interestEarned();
        }
        return total;
    }
    
    /**
     * Creates a statement for the {@link Customer} across all of the {@link Account}'s.
     * @return The statement.
     */
    public String getStatement() {
        return new CustomerStatement(name, accounts).build();
    }
}
