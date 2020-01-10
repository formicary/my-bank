package com.abc;

import com.abc.account.Account;

import java.util.ArrayList;
import java.util.List;

import static com.abc.util.StringFormatter.toDollars;

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
        for (Account account : accounts) {
            total += account.interestEarned();
        }
        return total;
    }

    public String getStatement() {
        double total = 0.0;
        StringBuilder statementStringBuilder = new StringBuilder();

        statementStringBuilder.append("Statement for ");
        statementStringBuilder.append(name);
        statementStringBuilder.append("\n");

        for (Account account : accounts) {
            statementStringBuilder.append("\n");
            statementStringBuilder.append(account.generateStatement());
            statementStringBuilder.append("\n");
            total += account.sumTransactions();
        }

        statementStringBuilder.append("\n");
        statementStringBuilder.append("Total In All Accounts ");
        statementStringBuilder.append(toDollars(total));
        return statementStringBuilder.toString();
    }

}
