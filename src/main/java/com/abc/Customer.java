package com.abc;

import java.util.ArrayList;
import java.util.List;

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
        for (Account acc : accounts) {
            total += acc.interestEarned();
        }
        return total;
    }

    public String getStatement() {
        String statement = "Statement for " + this.name + "\n\n";

        double total = 0.0;
        for (Account acc : accounts) {
            statement += acc.getStatement() + "\n";
            total += acc.getBalance();
        }

        statement += "Total In All Accounts " + Transaction.toDollars(total);
        return statement;
    }
}
