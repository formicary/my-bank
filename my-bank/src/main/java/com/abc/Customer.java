package com.abc;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Collection;
import java.util.regex.Matcher;

import static java.lang.Math.abs;

/**
* This class is a representation of a bank customer.
*
* A customer has an id, name and a list of accounts.
*/
public class Customer {
    private long id;
    private String name;
    private List<Account> accounts;

    /**
     * Constructor for creating a Customer object.
     * @param name the name of the customer
     */
    public Customer(long bankID, String name) {
        this.id = bankID;
        setName(name);
        this.accounts = new ArrayList<Account>();
    }

    /**
     * Validates and sets the customer name.
     * @param customerName the name supplied by the customer
     */
    public void setName(String customerName) {
        if (customerName.matches( "[a-zA-z]+([ '-][a-zA-Z]+)*" ))
            this.name = customerName;
        else
            throw new IllegalArgumentException("The name can contain only English letters and the following symbols: - \' ");
    }


    /**
     * Returns the name of the customer.
     * @return the name of the customer
     */
    public String getName() {
        return name;
    }

    /**
     * Opens a new account for the customer.
     */
    public void openAccount(Account account) {
        accounts.add(account);
    }

    /**
     * Returns the number of accounts.
     * @return the number of accounts the customer has
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * Returns the list with accounts for the customer.
     * @return the list with accounts
     */
    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    /**
     * Calculates the total amount of interest that this customer has earned across all accounts.
     * @return the total amount of interest
     */
    public double totalInterestEarned() {
        double total = 0;
        
        for (Account a : accounts)
            total += a.interestEarned();

        return total;
    }

    /**
     * Generates a bank statement for the customer, containing information from all accounts.
     * @return the statement 
     */
    public String getStatement() {
        String statement = "Statement for " + getName() + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.currentBalance();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        
        return statement;
    }

    /**
     * Generates a bank statement for the customer, containing information for a single account.
     * @param a the account for which the statement will be 
     * @return the statement
     */
    private String statementForAccount(Account a) {
        // Use StringBuilder instead of concatenating strings so as to avoid all the unnecessary copying
        StringBuilder statementBuilder = new StringBuilder();

       //Translate to pretty account type
        switch(a.getAccountType()){
            case Account.CHECKING:
                statementBuilder.append("Checking Account\n");
                break;
            case Account.SAVINGS:
                statementBuilder.append("Savings Account\n");
                break;
            case Account.MAXI_SAVINGS:
                statementBuilder.append("Maxi Savings Account\n");
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.getTransactions()) {
            statementBuilder.append("  " + (t.transactionType()) + " " + toDollars(t.getAmount()) + "\n");
            total += t.getAmount();
        }
        statementBuilder.append("Total " + toDollars(total));
        
        return statementBuilder.toString();
    }

    /**
     * Outputs the money amount in a formatted fashion.
     * @param amount amount of money
     * @return the formatted string
     */
    private String toDollars(double amount) {
        return String.format("$%,.2f", abs(amount));
    }

    /**
     * Transfers money between a customer's account.
     * @param amount amount of money
     * @param source account to transfer money from
     * @param destination account to transfer money to 
     */
    public void transferToAccount(double amount, Account source, Account destination) {
        // Make sure both accounts are valid
        if (!(this.getAccounts().contains(source))) {
            System.out.println("Error! Transfer failed. Source account does not exist.");
            return;
        }
        if (!(this.getAccounts().contains(destination))) {
            System.out.println("Error! Transfer failed. Destination account does not exist.");
            return;
        }
        
        /*
        * The following operation should be atomic.
        * I am not realizing that requirement due to time constraints.
        */
        source.withdraw(amount);
        destination.deposit(amount);
        System.out.println("Transfer completed.");
    }
}
