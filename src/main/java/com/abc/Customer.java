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
            statement.append("\n").append(a.statementForAccount()).append("\n");
            total += a.getBalance();
        }
        statement.append("\nTotal In All Accounts ").append(Transaction.toDollars(total));
        return statement.toString();
    }

    public void transferFunds(Account source, Account dest, double amount){
        // A customer must withdraw from their own account
        if (!accounts.contains(source)){
            throw new IllegalArgumentException("Source account does not belong to customer");
        }

        // A customer can only transfer to their own account
        if (!accounts.contains(dest)){
            throw new IllegalArgumentException("Destination account does not belong to customer");
        }

        source.withdraw(amount);
        dest.deposit(amount);

    }

}
