package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name; //Customer name
    private List<Account> accounts; //List of all the account owned by customer

    /**
     * Constructor for the Customer class
     * @param name customers name
     */
    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    /**
     * Function that opens an account for the customer
     * @param account the account being opened
     * @return the customer
     */
    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    /**
     * Function that returns the number of account a customer has
     * @return the number of accounts
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * Function that retrieves an account from the account name provided
     * @param accountName the account name used to retrieve corresponding account
     * @return the account that matches account name
     */
    public Account retrieveAccount(String accountName){
        if(getNumberOfAccounts() < 0){
            throw new NullPointerException("you have no accounts");
        }

        Account account = null;

        for(int i = 0; i<getNumberOfAccounts(); i++) {
            if (accounts.get(i).getAccountName().equals(accountName)) {
                account = accounts.get(i);
            }
            if(account != null){
                break;
            }
        }

        if(account == null){
            throw new IllegalArgumentException("account name doesn't exist");
        }

        return account;
    }

    /**
     * Function that calculates total interest earned by customer from all their account
     * @return total interest earned by customer
     */
    public double totalInterestEarned() {
        double total = 0;
        for (Account account : accounts)
            total += account.interestEarned();
        return total;
    }

    /**
     * Function that creates the customers statement
     * @return customers statement
     */
    public String getStatement() {
        StringBuilder stringBuilder = new StringBuilder("Statement for " + name + "\n");
        double total = 0.0;
        for (Account account : accounts) {
            stringBuilder.append("\n");
            stringBuilder.append(statementForAccount(account));
            stringBuilder.append("\n");
            total += account.sumTransactions();
        }
        stringBuilder.append("\nTotal In All Accounts ");
        stringBuilder.append(toDollars(total));
        return stringBuilder.toString();
    }

    /**
     * Function to create a statement for an account
     * @param account the account for which the stament is being created
     * @return the account statement
     */
    private String statementForAccount(Account account) {
        StringBuilder stringBuilder = new StringBuilder();

        switch (account.getAccountType()) {
            case Account.CHECKING:
                stringBuilder.append("Checking Account\n");
                break;
            case Account.SAVINGS:
                stringBuilder.append("Savings Account\n");
                break;
            case Account.MAXI_SAVINGS:
                stringBuilder.append("Maxi Savings Account\n");
                break;
        }

        double total = 0.0;
        for (Transaction transaction : account.getTransactions()) {
            stringBuilder.append("  ");
            stringBuilder.append((transaction.getAmount() < 0 ? "withdrawal" : "deposit"));
            stringBuilder.append(" ");
            stringBuilder.append(toDollars(transaction.getAmount()));
            stringBuilder.append("\n");
            total += transaction.getAmount();
        }
        stringBuilder.append("Total ");
        stringBuilder.append(toDollars(total));
        return stringBuilder.toString();
    }

    /**
     * Function to format a number into dollars
     * @param amount number to be created into dollars
     * @return amount converted into dollars
     */
    private String toDollars(double amount){
        return String.format("$%,.2f", abs(amount));
    }

    /**
     * Getter for the list of accounts
     * @return list of accounts
     */
    public List<Account> getAccounts() {
        return accounts;
    }

    /**
     * Getter for name of the customer
     * @return name of the customer
     */
    public String getName() {
        return name;
    }

}
