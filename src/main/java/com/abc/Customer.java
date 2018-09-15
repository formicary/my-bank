package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

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
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }
    
    /**
     * Creates a statement for the {@link Customer} across all of the {@link Account}'s.
     * @return The statement.
     */
    public String getStatement() {
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

    private String statementForAccount(Account a) {
    	//Translate to pretty account type
        String s = a.getAccountName() + "\n";;
        

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {
            s += "  " + (t.getAmount() < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.getAmount()) + "\n";
            total += t.getAmount();
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
