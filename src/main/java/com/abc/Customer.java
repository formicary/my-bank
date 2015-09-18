package com.abc;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        if(name == null || name == "")
        	throw new IllegalArgumentException("name must be specified");
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
    
    public Customer transfer(Account source, Account destination, double amount) {
    	if (amount <= 0)
    		throw new IllegalArgumentException("amount must be greater than zero");
    	if(!accounts.contains(source))
    		throw new IllegalArgumentException("transfer from an account not owned by the customer not allowed");
    	source.withdraw(amount);
    	destination.deposit(amount);
    	return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarnedOnDay(Date d) {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarnedOnDay(d);
        return total;
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + (total < 0 ? "-" : "") + toDollars(total);
        return statement;
    }

    private String statementForAccount(Account a) {
    	String s = a.getAccountType() + "\n";

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        s += "Total " + (total < 0 ? "-" : "") + toDollars(total);
        return s;
    }

    private String toDollars(double d){
        return DecimalFormat.getCurrencyInstance(Locale.US).format(abs(d));
    }
}
