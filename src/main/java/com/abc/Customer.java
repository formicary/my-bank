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
        StringBuilder statement = new StringBuilder();
        double total = 0.0;

        statement.append("Statement for ");
        statement.append(name);
        statement.append("\n");

        for (Account account : accounts) {
            statement.append("\n");
            statement.append(statementForAccount(account));
            statement.append("\n");

            total += account.sumTransactions();
        }
        statement.append("\n");
        statement.append("Total In All Accounts ");
        statement.append(toDollars(total));

        return statement.toString();
    }

    private String statementForAccount(Account account) {
        StringBuilder text = new StringBuilder();
        text.append(account.getType());

        //Now total up all the transactions
        double totalAmount = 0.0;
        for (Transaction transaction : account.transactions) {
            text.append("  ");
            text.append(transaction.amount < 0 ? "withdrawal" : "deposit");
            text.append(" ");
            text.append(toDollars(transaction.amount));
            text.append("\n");

            totalAmount += transaction.amount;
        }

        text.append("Total ");
        text.append(toDollars(totalAmount));

        return text.toString();
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
