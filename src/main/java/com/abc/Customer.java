package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * Controls an individual customer in the bank, each customer can open multiple accounts
 */
public class Customer {
    /**
     * The customer's name
     */
    private String name;
    /**
     * A list of the customer's accounts
     */
    private List<Account> accounts;

    /**
     * Constructor taking a name for the customer and initialising the accounts list
     * @param name the customer's name
     */
    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    /**
     * Getter for the customer's name
     * @return the customer's name
     */
    public String getName() {
        return name;
    }

    /**
     * Allows the customer to open a new account under their name for the given account type
     * @param accountType the type of account
     * @return the account object created
     */
    public Account openAccount(Account.AccountType accountType) {
        Account account = new Account(accountType);
        accounts.add(account);

        return account;
    }

    /**
     * Allows the customer to transfer money from one of their accounts to another account
     * @param source the account to take money from, must be owned by the customer
     * @param destination the account to give money to
     * @param amount the amount of money to transfer
     */
    public void transfer(Account source, Account destination, double amount) {
        // first check that the customer owns the source account, if the indexOf method returns -1 it indicates
        // that the object wasn't found in the list
        if (accounts.indexOf(source) == -1) {
            throw new IllegalArgumentException("source account not owned by customer");
        }

        if (source.calculateBalance() < amount) {
            throw new IllegalArgumentException("source account has an insufficient balance");
        }

        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be larger than 0");
        }

        // add a transfer out transaction to the source account and a transfer in transaction to the destination
        source.addTransaction(new Transaction(-amount, Transaction.TransactionType.TRANSFER_OUT));
        destination.addTransaction(new Transaction(amount, Transaction.TransactionType.TRANSFER_IN));
    }

    /**
     * Get the number of accounts the customer has
     * @return the number of accounts
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * Calculate the total amount of interest that the customer has been given across all their accounts
     * @return the total interest
     */
    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.getTotalInterestEarned();
        return total;
    }

    /**
     * Called by the customer's bank to check each of the customer's accounts and grant them interest if eligible
     */
    public void updateInterestPayments() {
        for (Account a : accounts) {
            // only grant interest to the account if they haven't already received it in the last day
            if (a.checkInterestEligibility()) {
                a.addTransaction(new Transaction(a.getDailyInterestEarned(), Transaction.TransactionType.INTEREST));
            }
        }
    }

    /**
     * Constructs a report detailing the transaction history and balance for each of the customer's accounts
     * @return a string containing the report
     */
    public String getStatement() {
        StringBuilder statement = new StringBuilder();
        statement.append("Statement for ")
            .append(name)
            .append('\n');

        double total = 0.0;

        for (Account a : accounts) {
            statement.append('\n')
                .append(statementForAccount(a))
                .append('\n');
            total += a.calculateBalance();
        }
        statement.append("\nTotal In All Accounts ")
            .append(toDollars(total));
        return statement.toString();
    }

    /**
     * Used by the getStatement function to create a report for a specific account
     * @param a the account to generate a report for
     * @return a string containing the report
     */
    private String statementForAccount(Account a) {
        StringBuilder statement = new StringBuilder();

       //Translate to pretty account type
        switch(a.getAccountType()){
            case CHECKING:
                statement.append("Checking Account\n");
                break;
            case SAVINGS:
                statement.append("Savings Account\n");
                break;
            case MAXI_SAVINGS:
                statement.append("Maxi Savings Account\n");
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.getTransactions()) {
            statement.append("  ")
                .append(t.getTransactionType().name())
                .append(' ')
                .append(toDollars(t.getAmount()))
                .append('\n');
            total += t.getAmount();
        }
        statement.append("Total ")
            .append(toDollars(total));
        return statement.toString();
    }

    /**
     * Utility method to convert a double into a more regular currancy format
     * @param d the value to be converted
     * @return the formatted string
     */
    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
