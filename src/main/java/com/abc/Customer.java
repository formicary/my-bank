package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private final String name;
    private final List<Account> accounts;

    public Customer(final String name) {
        this.name = name;
        this.accounts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(final Account account) {
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        return accounts.stream().mapToDouble(Account::interestEarned).sum();
    }

    public String getStatement() {
        StringBuilder statement = new StringBuilder(String.format("Statement for %s\n", name));
        double total = 0.0;
        for (Account a : accounts) {
            statement.append(String.format("\n%s\n", statementForAccount(a)));
            total += a.getBalance();
        }
        statement.append(String.format("\nTotal In All Accounts %s", StringUtils.toDollars(total)));
        return statement.toString();
    }

    private String statementForAccount(final Account a) {
        StringBuilder s = new StringBuilder(String.format("%s Account\n", a.getAccountType().readableName));

        for (Transaction t : a.transactions) {
            String transactionType = t.amount < 0 ? "withdrawal" : "deposit";
            s.append(String.format("  %s %s\n", transactionType, StringUtils.toDollars(t.amount)));
        }
        s.append(String.format("Total %s", StringUtils.toDollars(a.getBalance())));
        return s.toString();
    }
}
