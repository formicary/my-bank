package com.abc;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class Customer {

    //Fields to represent a user
    private final String name;
    private final int customerID;
    private final ArrayList<Account> accounts;

    /**
     * Creates a new instance of Name
     *
     * @param name the name of the customer
     * @param customerID the id of the customer
     *
     */
    public Customer(String name, int customerID) {
        this.name = name;
        this.customerID = customerID;
        this.accounts = new ArrayList<>();
    }

    /**
     *
     * @return the name of the customer
     */
    public String getName() {
        return name;
    }

    /**
     * @return ID of the customer
     */
    public int getCustomerID() {
        return customerID;
    }

    /**
     *
     * @param account
     * @return Create an account under the customer
     */
    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    /**
     *
     * @return The number of account under the customer
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     *
     * @return The total amount of interest earned
     */
    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts) {
            total += a.interestEarned();
        }
        return total;
    }

    /**
     *
     * @param from an account to be withdraw from
     * @param to an account to be deposit to
     * @param amount amount to transfer
     */
    // Assuming customer can only have one of each account, 
    // i.e. one savings one checking one maxi-saving
    // as it wouldnt make sense if a customer have two savings account
    public void transferBetweenAccounts(Account from, Account to, double amount) {
        if (from.getAccountType() != to.getAccountType() && from.getBalance() > amount) {
            from.withdraw(amount);
            to.deposit(amount);
        }
    }

    /**
     *
     * @return A bank statement for the customer detailing their name, statement
     * for each of the account under the customer, the sum of transaction of the
     * customer and the total amount of money available.
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
     *
     * @param a
     * @return A bank statement for a particular account
     */
    private String statementForAccount(Account a) {
        String s = "";

        //Translate to pretty account type
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

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        s += "Total " + toDollars(total);
        return s;
    }

    /**
     *
     * @param type account type, i.e. 1 for checking, 2 for saving, 3 for maxi
     * saving
     * @return an account object that matches with the provided type if exists
     */
    public Account getAccount(int type) {
        for (Account account : accounts) {
            if (account.getAccountType() == type) {
                return account;
            }
        }
        throw new IllegalArgumentException("Account doesn't exist");
    }

    /**
     * Deposit amount of interest earned according to account type to each
     * account
     */
    public void accureInterest() {
        for (Account a : accounts) {
            a.deposit(a.interestEarned());
        }
    }

    /**
     *
     * @param d
     * @return The amount in dollars
     */
    private String toDollars(double d) {
        return String.format("$%,.2f", abs(d));
    }
}
