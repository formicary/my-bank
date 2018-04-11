package com.abc;

import java.time.LocalDateTime;
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
        StringBuilder statement = new StringBuilder("Statement for " + name + "\n");
        double total = 0.0;
        for (Account a : accounts) {
            statement.append("\n").append(statementForAccount(a)).append("\n");
            total += a.getBalance();
        }
        statement.append("\nTotal In All Accounts ").append(toDollars(total));
        return statement.toString();
    }

    private String statementForAccount(Account account ) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(account.getAccountName() + "\n");
        //Now total up all the transactions
        for (Transaction t : account.getTransactions()) {
            stringBuilder.append("\t");

            if (t.getAmount() < 0)
                stringBuilder.append("withdrawal");
            else
                stringBuilder.append("deposit");

            stringBuilder.append(" " + toDollars(t.getAmount()) + "\n");
        }
        stringBuilder.append("Total " + toDollars(account.getBalance()));

        return stringBuilder.toString();
    }

    private String toDollars(double d) {
        return String.format("$%,.2f", abs(d));
    }
}
