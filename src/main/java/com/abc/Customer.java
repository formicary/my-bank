package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;
    private final int customerID;
    
    private static int nextCustomerID;
    
    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
        this.customerID=nextCustomerID;
        nextCustomerID++;
    }

    public String getName() {
        return name;
    }
    
    public int getID() { 
        return customerID;
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
            total += a.totalInterestAccrued();
        return total;
    }
    
    public double totalBalance() {
        double total = 0;
        for (Account a : accounts)
            total += a.getBalance();
        return total;
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        for (Account a : accounts) {
            statement += "\n" + a.getStatement() + "\n";
        }
        statement += "\nTotal In All Accounts: " + HelperMethods.toDollars(totalBalance());
        return statement;
    }
    
    public List<Integer> getAccountIDs()
    {
        List<Integer> accountIDs = new ArrayList<>();
        for (Account a : accounts) {
            accountIDs.add(a.getAccountID());
        }
        return accountIDs;
    }
    
    //returns null if no account found with that ID;
    public Account getAccountByID(int accountID){
        for (Account a : accounts) {
            if (accountID==a.getAccountID()) return a;
        }
        return null;
    }
    
    //returns false if not enough money in account
    public Boolean transferBetweenAccounts(double amount, Account fromAccount, Account toAccount){
        if (amount<0) throw new IllegalArgumentException("amount must be greater than zero");
        if (fromAccount==null || toAccount==null) throw new IllegalArgumentException("Accounts cannot be null");
        Boolean success =fromAccount.withdraw(amount);
        if (!success) return false;
        toAccount.deposit(amount);
        return true;
    }
}
