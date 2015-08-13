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
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    public String getStatement() {
        String statement = null;
        
        if (accounts.isEmpty())
            statement = name + " has no open accounts";
        else {
        	statement = "Statement for " + name + "\n";
        	double total = 0.0;
	        for (Account a : accounts) {
	            statement += "\n" + a.getAccountType().toString() + ":\n" + statementForAccount(a) + "\n";
	            total += a.getAccountTotal();
	        }
	        statement += "\nTotal In All Accounts " + toDollars(total);
        }
        return statement;
    }

    private String statementForAccount(Account a) {
        String s = "";

        //Display all transactions on the account
        for (Transaction t : a.transactions) {
            s += "  " + t.getTransactionType() + " of " + toDollars(t.getTransactionAmount()) + "\n";
        }
        //Display total of all transactions
        s += "Total " + toDollars(a.getAccountTotal());
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
