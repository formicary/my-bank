package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;

    /**
     * Customer class constructor. Takes a name as a parameter and initialises
     * the accounts array list
     * @param name String containing the name of the customer
     */
    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    /**
     * Returns name of the customer
     * @return name String name
     */
    public String getName() {
        return name;
    }

    /**
     * Adds the the given Account parameter to the accounts array list and returns
     * the adjusted Customer object
     * @param account Account object
     * @return Customer The Customer object on which the function was called
     */
    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    /**
     * Returns the size of the accounts array list
     * @return int Number of accounts
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * Returns the total interest earned over all accounts the customer owns
     * @return double Amount of interest
     */
    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts) {
            total += a.interestEarned();
        }
        return total;
    }

    /**
     * Adds the customer name, account information (from statementForAccount)
     * and account totals (sumTransactions) for each account to a template
     * @return String Complete statement in dollar format
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

    /**
     * Identifies the account type and totals the transactions for that account.
     * Returns this information as a String
     * @param a Account object associated with the customer
     * @return String Statement containing account type and total in dollar format
     */
    private String statementForAccount(Account a) {
        String s = "";

        // Translate to pretty account type
        switch (a.getAccountType()) {
        case Account.CHECKING:
            s += "Checking Account\n";
            break;
        case Account.SAVINGS:
            s += "Savings Account\n";
            break;
        case Account.MAXI_SAVINGS:
            s += "Maxi Savings Account\n";
            break;
        }

        // Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        s += "Total " + toDollars(total);
        return s;
    }

    /**
     * Converts a double to a String in the form of $0.00
     * @param d Double value of amount to be converted
     * @return String Amount converted to String value
     */
    private String toDollars(double d) {
        return String.format("$%,.2f", abs(d));
    }
}
