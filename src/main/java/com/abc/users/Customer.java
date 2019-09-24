package com.abc.users;

import com.abc.accounts.Account;

import java.util.ArrayList;
import java.util.List;

import static com.abc.util.CurrencyFormatter.toDollars;

public class Customer {

    //TODO May extend from a User class as the addition of a bank manager suggests more than one type of user

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
        return accounts.stream().mapToDouble(Account::interestEarned).sum();
    }

    public String getStatement() {
        StringBuilder statement = new StringBuilder("Statement for " + name + "\n");
        double total = 0.0;
        for (Account a : accounts) {
            statement.append("\n").append(a.getStatement()).append("\n");
            total += a.sumTransactions();
        }
        statement.append("\nTotal In All Accounts ").append(toDollars(total));
        return statement.toString();
    }
}
