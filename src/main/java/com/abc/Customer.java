package com.abc;

import java.util.*;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private Map<Integer, Account> accounts;

    // Like in the comments below, customers should be differentiated through some unique identifier
    public Customer(String name) {
        this.name = name;
        this.accounts = new HashMap<Integer, Account>();
    }

    public String getName() {
        return name;
    }

    //Returning a Customer when opening an account doesn't really make sense
    //(If the motivation behind it was to avoid having Customers with no accounts, that can be implemented in a different way)
    //Refactored this method to return a unique identifier of the newly created account in order to:
    //  1. Allow the creation of more than one account of the same type
    //  2. Allow the Customer to later access his account based on the id (similar to real life)
    public int openAccount(AccountType accountType) {
        // In a real-life application, accountId would be computed in a less primitive way to ensure uniqueness
        int accountId = accounts.size() + 1;
        accounts.put(accountId, new Account(accountType));

        return accountId;
    }

    public Account getAccount(int accountId){
        if(accounts.get(accountId) == null){
            throw new IllegalArgumentException("The account with no. " + accountId + " does not exist");
        }
        return accounts.get(accountId);
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public void transferBetweenAccounts(int fromAccountId, int toAccountId, double amount){

        Account fromAccount = accounts.get(fromAccountId);
        Account toAccount = accounts.get(toAccountId);

        if(fromAccount == null){
            throw new IllegalArgumentException("The id account you are trying to transact from does not exist");
        }
        if(toAccount == null){
            throw new IllegalArgumentException("The id account you are trying to transact to does not exist");
        }
        if(amount < 0){
            throw new IllegalArgumentException("amount must be greater than zero");
        }
        fromAccount.withdraw(amount);
        toAccount.deposit(amount);
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account account : accounts.values()) {
            total += account.interestEarned();
        }
        return total;
    }

    public String getStatement() {
        String statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account account : accounts.values()) {
            statement += "\n" + statementForAccount(account) + "\n";
            total += account.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    private String statementForAccount(Account a) {
        String s = a.getAccountType().getName() + "\n";

        //Total up all the transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        s += "Total " + toDollars(total);

        System.out.println(s);

        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
