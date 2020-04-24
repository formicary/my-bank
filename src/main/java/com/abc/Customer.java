package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;

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
        double total = 0;
        for (Account a : accounts)
            total += a.calculateInterest();
        return total;
    }

    public String getStatement() {
        StringBuilder statement = new StringBuilder("Statement for ");
        statement.append(name);
        statement.append("\n");
        double total = 0.0;
        for (Account account : accounts) {
            statement.append("\n");
            statement.append(statementForAccount(account));
            statement.append("\n");

            total += sumTransactions(account);
        }
        statement.append("\nTotal In All Accounts ");
        statement.append(toDollars(total));
        return statement.toString();
    }

    public double sumTransactions(Account account) {
        double amount = 0.0;
        for (Transaction t: account.getTransactions())
            amount += t.getAmount();
        return amount;
    }

    private String statementForAccount(Account account) {

        StringBuilder sb = new StringBuilder(account.getAccountType());
        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : account.getTransactions()) {
            sb.append(" ");
            sb.append(t.getTransactionType());
            sb.append(" ");
            sb.append(toDollars(t.getAmount()));
            sb.append("\n");
            total += t.getAmount();
        }
        sb.append("Total ");
        sb.append(toDollars(total));
        return sb.toString();
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
