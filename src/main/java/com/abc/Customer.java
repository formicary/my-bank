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
    public double totalInterestEarned() {
        double total = 0.0;
        for (Account a : accounts)
            total += a.dailyInterestEarned();
        return total;
    }
    public String getStatement() {
        String statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + a.getStatement() + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }
    
    public void Transfer(int from, int to, double amount) throws IllegalArgumentException
    {
    	if (to>=accounts.size() || from>=accounts.size() ||
    			to<0 || from <0)
    	{
    		throw new IllegalArgumentException("Specified account(s) does not exist");
    	}
    	else
    	{
    		// if withdrawal fails, no money is transfered, exception is thrown and propagates upwards
        	accounts.get(from).withdraw(amount);
    		accounts.get(to).deposit(amount);
    	}
    }
    public Account getAccount(int index) throws IllegalArgumentException
    {
    	if (index<0 || index>=accounts.size())
    		throw new IllegalArgumentException("Account does not exists");
    	else
    		return accounts.get(index);
    }
    
    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
