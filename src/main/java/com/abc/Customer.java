package com.abc;

import com.abc.Account.Account;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private ArrayList<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }

    public void addAccount(Account account) {
        accounts.add(account);
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
