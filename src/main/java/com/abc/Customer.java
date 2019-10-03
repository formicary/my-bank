package com.abc;

import com.abc.accounts.Account;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    public String getStatement() {
        String statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + a.getStatement() + "\n";
            total += a.getBalance();
        }
        statement += "\nTotal In All Accounts " + String.format("$%,.2f", abs(total));
        return statement;
    }

    // Transfer money from an account to another. Make sure that the accounts belong
    // to the customer
    public void transfer(double amount, Account fromAccount, Account toAccount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than 0");
        } else if (!accounts.contains(fromAccount) || !accounts.contains(toAccount)) {
            throw new IllegalArgumentException("Customer does not own one of the accounts");
        } else {
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);
        }
    }
}
