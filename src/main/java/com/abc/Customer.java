/**
 * A customer must have a name and can hold several accounts at a Bank.
 */

package com.abc;

import java.text.Format;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {

    private String name;
    private List<Account> accounts;

    /**
     * Create a customer and initialise his/her name as well as an empty list of accounts.
     * @param name
     */

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    /**
     * Get the name of the customer.
     * @return The name of the customer
     */

    public String getName() {
        return name;
    }

    /**
     * Change the name of the customer.
     * @param name The new name for the customer.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Open an account for this Customer.
     * @param account The account to be opened.
     * @return The instance of this specific Customer.
     */

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    /**
     * Transfer funds from one account to another.
     * @param amount The amount to transfer.
     * @param from The account to transfer money from.
     * @param to The account to transfer money to.
     */
    public void transfer (double amount, Account from, Account to) throws InvalidAmountException{

        from.withdraw(amount);
        to.deposit(amount);

    }

    /**
     * Get the number of accounts for this Customer.
     * @return The number of accounts.
     */

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * Calculate the total interest earned for this Customer.
     * @return The total interest earned.
     */

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    /**
     * Get the Customers statement which contains the Customer name,
     * statement for each account and a sum of all transactions.
     * @return The statement for this Customer.
     */

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + a.statementForAccount() + "\n";
            total += a.getBalance();
        }
        statement += "\nTotal In All Accounts " + Formatter.toDollars(total);
        return statement;
    }

    /**
     * Get a single account from a list of accounts.
     * @param index The index position within the list of accounts.
     * @return The selected account.
     */
    public Account getAccount (int index) {

        return accounts.get(index);
    }

    /**
     * Obtain all customer accounts as an unmodifiable collection.
     * @return All customer accounts.
     */
    public Collection<Account> getAllAccounts () {

        return Collections.unmodifiableCollection(accounts);
    }

}
