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
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    public String getStatement() {
        String statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + a.statementForAccount() + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + Transaction.toDollars(total);
        return statement;
    }

    public void transferFunds(Account source, Account dest, double amount){
        // A customer can only withdraw from its own account
        if (!accounts.contains(source)){
            throw new IllegalArgumentException("Source account does not belong to customer");
        }

        source.withdraw(amount);
        dest.deposit(amount);

    }

}
