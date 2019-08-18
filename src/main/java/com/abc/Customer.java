package com.abc;

import java.util.ArrayList;
import java.util.List;

import static com.abc.Utility.toDollars;
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

        for (Account account : accounts)
            total += account.interestEarned();

        return total;
    }

    public String getStatement() {
        StringBuilder statement = new StringBuilder("Statement for " + name + "\n");
        double total = 0;

        for (Account account : accounts) {
            statement.append("\n").append(account.getAccountStatement()).append("\n");
            total += account.getAccountValue();
        }

        statement.append("\nTotal In All Accounts ").append(toDollars(total));

        return statement.toString();
    }
}
