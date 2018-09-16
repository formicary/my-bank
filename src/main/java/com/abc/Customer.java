package com.abc;

import java.util.List;

/**
 * A {@link Customer} represents a customer of the {@link Bank}.
 */
public class Customer {
    private final String name;
    private final List<Account> accounts;
    
    /**
     * Constructs a {@link Customer}.
     * @param name The name of the customer.
     * @param accounts The customer's accounts.
     */
    public Customer(String name, List<Account> accounts) {
        this.name = name;
		this.accounts = accounts;
    }
    
    /**
     * Gets the name of the {@link Customer}.
     * @return See above.
     */
    public String getName() {
        return name;
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
