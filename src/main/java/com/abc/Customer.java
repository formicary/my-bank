package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;

    public Customer(String name) {
    	if (name.length()==0)	{
    		throw new IllegalArgumentException("name cannot be blank");
    	}
    	
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
            statement += "\n" + a.getStatementForAccount() + "\n";
        	total += a.getBalance();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        System.out.print("\n" + statement);
        return statement;
    }

	
    
    public void transfer (Account sender, Account receiver, double amount) {
    	if (sender != receiver)	{
    		
    		if (amount > sender.getBalance())	{
    			throw new IllegalArgumentException("You do not have enough funds to transfer");
    		}
    		sender.withdraw(amount);
    		receiver.deposit(amount);
    	}
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
