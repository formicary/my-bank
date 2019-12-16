package com.abc;

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

    public double getTotalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.getTotalInterestEarned();
        return total;
    }

    public String getStatement() {
        String statement = "Statement for " + this.name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + a.getStatement() + "\n";
            total += a.getBalance();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }
    
    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
    
}
