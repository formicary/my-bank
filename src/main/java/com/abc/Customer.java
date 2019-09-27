package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String name;
    private List<Account> accounts;

    //Customer must be added to a bank on instantiation. This avoids the same customer belonging to multiple banks.
    Customer(String name, Bank bank) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
        bank.addCustomer(this);
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(int accountType) {
        accounts.add(new Account(accountType));
        return this;
    }

    public void deposit(double dollars, int accountIndex) {
        try {
            isValidIndex(accountIndex);
        } catch (IllegalArgumentException e) {
            throw e;
        }

        accounts.get(accountIndex).deposit(dollars);
    }

    public void withdraw(double dollars, int accountIndex) {
        try {
            isValidIndex(accountIndex);
        } catch (IllegalArgumentException e) {
            throw e;
        }

        accounts.get(accountIndex).withdraw(dollars);
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public String getStatement() {
        String statement;
        statement = "Statement for " + name + "\n";
        double total = 0;
        for (Account a : accounts) {
            statement += "\n" + a.createStatement() + "\n";
            total += a.getBalanceDollars();
        }
        statement += "\nTotal In All Accounts " + String.format("$%,.2f", total);
        return statement;
    }

    public void transfer(int toIndex, int fromIndex, double dollars) {
        accounts.get(fromIndex).withdraw(dollars);
        accounts.get(toIndex).deposit(dollars);
    }

    double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    private void isValidIndex(int accountIndex) {
        if (accountIndex < 0 || accountIndex >= accounts.size())
            throw new IllegalArgumentException("invalid account index");
    }
}
