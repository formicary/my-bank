package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;

    /**
     * Customer object constructor
     * @param name Name of customer to be added
     */
    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }

    /**
     * @param account Account to be added to the customer
     * @return Customer object after addition of new account
     */
    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * @return Total interest paid across all accounts for the customer
     */
    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    /**
     * @return statement of all the accounts under this customer's name
     */
    public String getStatement() {
        StringBuilder statement = new StringBuilder();
        statement.append("Statement for " + name + "\n");

        double total = 0.0;
        for (Account a : accounts) {
            statement.append("\n" + a.statementForAccount() + "\n");
            total += a.getBalance();
        }
        statement.append("\nTotal In All Accounts " + toDollars(total));

        return statement.toString();
    }

    String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }

    /**
     * Called by the bank for each customer to
     * update each of their account's balances with daily interest rates
     */
    public void dailyInterest() {
        for (Account a: accounts) {
            a.applyInterest();
        }
    }

    /**
     * @param fromAccount The account from which money is being transfered out of
     * @param toAccount The account from which money is being transfered to
     * @param balance The amount of money being transfered
     * @return true if successful, else false
     */
    public boolean accountTransfer(Account fromAccount, Account toAccount, double balance) {
        if (fromAccount.getBalance() < balance){
            System.out.println("Not enough funds to make transfer");
            return false;
        } else {
            fromAccount.withdraw(balance);
            toAccount.deposit(balance);
            return true;
        }
    }
}
