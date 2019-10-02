package com.abc;

import java.util.ArrayList;
import java.util.List;

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

    public void openAccount(Account account) {
        accounts.add(account);
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account account : accounts)
            total += account.getInterest();
        return total;
    }

    public String getStatement() {
        String statement = "Statement for " + name + "\n"; 
        for (Account account : accounts) {
            statement += "\n" + account.getStatement() + "\n";
        }
        statement += String.format("\nTotal for all accounts: $%.2f", sumAccounts());
        return statement;
    }
    
    private double sumAccounts() {
    	double sum = 0;
    	for (Account accounts : accounts) {
    		sum += accounts.sumTransactions();
    	}
    	return sum;
    }
    
    public void transfer(Account sourceAccount, Account destinationAccount, double amount) {
    	sourceAccount.withdraw(amount);
    	destinationAccount.deposit(amount);
    }
}
