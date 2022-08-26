package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private final String name;
    private final List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<>();
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
        double total = 0.0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    private double sumAllTransactions() {
        double total = 0.0;
        for (Account account :
                accounts) {
            total += account.sumTransactions();
        }
        return total;
    }

    public String getStatement() {
        final String lineBreak = "\n";
        StringBuilder statement = new StringBuilder("Statement for ");
        statement.append(name);
        statement.append(lineBreak);
        for (Account account : accounts) {
            statement.append(lineBreak);
            statement.append(statementForAccount(account));
            statement.append(lineBreak);
        }
        statement.append(lineBreak);
        statement.append("Total In All Accounts ");
        statement.append(toDollars(sumAllTransactions()));
        return statement.toString();
    }

    private String statementForAccount(Account account) {
        final String lineBreak = "\n";
        StringBuilder sb = new StringBuilder(account.getAccountType().getPrettyName());
        sb.append(lineBreak);
        for (Transaction t : account.transactions) {
            if (t.getAmount() < 0) {
                sb.append("  withdrawal ");
            } else {
                sb.append("  deposit ");
            }
            sb.append(toDollars(t.getAmount()));
            sb.append(lineBreak);
        }
        sb.append("Total ");
        sb.append(toDollars(account.sumTransactions()));
        return sb.toString();
    }

    private String toDollars(double d) {
        return String.format("$%,.2f", abs(d));
    }
}
