package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
	
    private String name;
    private List<Account> accounts = new ArrayList<>();

    public Customer(String name) {
        this.name = name;
    }

    public void openAccount(Account account) {
        accounts.add(account);
    }
    
    public void transfer(Account accountFrom, Account accountTo, double amount) {
    	if (amount <= 0) {
    		throw new IllegalArgumentException("amount must be greater than zero");
    	} else if (!accounts.contains(accountFrom) || !accounts.contains(accountTo)) {
    		throw new IllegalArgumentException("customer accounts invalid, customer must transfer between two valid accounts");
    	} else {
    		accountFrom.withdraw(amount);
            accountTo.deposit(amount);
    	}
    }
    
    public String getName() {
        return name;
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
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.getBalance();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    private String statementForAccount(Account a) {
        String s = "";

       //Translate to pretty account type
        s += a.getAccountType();
        s += "\n";

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.getTransactions()) {
            s += "  " + (t.getAmount() < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.getAmount()) + "\n";
            total += t.getAmount();
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
