package com.abc;

import com.abc.Accounts.Account;

import java.util.ArrayList;
import java.util.List;

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

    public List<Account> getAccounts() {
        return accounts;
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
            statement.append("\n").append(a.accountStatement()).append("\n");
            total += a.getBalance();
        }
        statement.append("\nTotal In All Accounts ").append(Transaction.toDollars(total));
        return statement.toString();
    }

    public void transferFunds(Account source, Account dest, double amount) {
        // A customer can only withdraw from their own account
        if (!accounts.contains(source)) {
            throw new IllegalArgumentException("Source account does not belong this customer");
        }

        // A customer can only transfer to their own account
        if (!accounts.contains(dest)) {
            throw new IllegalArgumentException("Destination account does not belong this customer");
        }

        source.withdraw(amount);
        dest.deposit(amount);

    }

}
