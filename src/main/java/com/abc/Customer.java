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
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + a.getAccountStatement() + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + Formatter.toDollars(total);
        return statement;
    }

    public void transferFunds(double amount, Account fromAccount, Account toAccount) {
        fromAccount.withdraw(amount);
        toAccount.deposit(amount);
    }

    public static class Formatter {
        public static String toDollars(double d){
            return String.format("$%,.2f", abs(d));
        }

    }



}
