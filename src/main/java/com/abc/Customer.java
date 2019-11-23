package com.abc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private Map<Integer, Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new HashMap<Integer, Account>();
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(Account account) {
    	accounts.put(account.getAccountNumber(), account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }
    
    public Account getAccount(int accountNumber) throws Exception {
    	if (accounts.containsKey(accountNumber))
    		return accounts.get(accountNumber);
    	else
    		throw new Exception("Account not found");
    }
    
    public void transfer(int fromAccountNumber, int toAccountNumber, double amount) {
    	accounts.get(fromAccountNumber).withdraw(amount);
    	accounts.get(toAccountNumber).deposit(amount);
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts.values())
            total += a.interestEarned();
        return total;
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts.values()) {
            statement += "\n" + a.toString() + "\n";
            total += a.updateBalance();
        }
        statement += "\nTotal In All Accounts " + HelperClass.toDollars(total);
        return statement;
    }
}
