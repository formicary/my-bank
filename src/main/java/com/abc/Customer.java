package com.abc;

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

    private static String toDollars(double d) {
        return String.format("$%,.2f", abs(d));
    }

    public String getStatement() {
        StringBuilder statement = new StringBuilder("Statement for " + name + "\n");
        double total = 0.0;
        for (Account a : accounts) {
            statement.append("\n").append(a.statement()).append("\n");
            total += a.sumTransactions();
        }
        statement.append("\nTotal In All Accounts ").append(toDollars(total));
        return statement.toString();
    }

    public void transfer(Account transferFrom, Account transferTo, double amount) {
        if (transferFrom == transferTo) {
            throw new IllegalArgumentException("must transfer between 2 different accounts");
        } else if (!accounts.contains(transferFrom)) {
            throw new IllegalArgumentException(name + " must own transfer from account");
        } else if (!accounts.contains(transferTo)) {
            throw new IllegalArgumentException(name + " must own transfer to account");
        } else {
            transferFrom.withdraw(amount); // If withdraw fails, deposit is not executed
            transferTo.deposit(amount);
        }
    }
}
