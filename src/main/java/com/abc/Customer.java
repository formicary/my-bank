package com.abc;

import java.util.ArrayList;

import java.util.List;

public class Customer {
    
    private final String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }

    public void openAccount(Account account) {
        accounts.add(account);
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
        StringBuilder statement = new StringBuilder();
        statement.append("Statement for " + name + "\n");
        double total = 0.0;
        for (Account a : accounts) {
            statement.append("\n" + a.statementForAccount() + "\n");
            total += a.sumTransactions();
        }
        statement.append("\nTotal In All Accounts " + Utils.toDollars(total));
        return statement.toString();
    }
   
}
