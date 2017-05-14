package com.abc;

import java.util.ArrayList;
import java.lang.Exception;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private ArrayList<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }
    
    public ArrayList<Account> getAccounts() {
    	return accounts;
    }

    public Customer openAccount(Account account) {
        boolean added = accounts.add(account);
        if (added) {
            return this;
        } else {
        	return null;
        }
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0.0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    public String getStatement() {
        String statement = "Statement for " + name + ":\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.getBalance();
        }
        statement += "\nTotal In All Accounts: " + toDollars(total);
        return statement;
    }

    private String statementForAccount(Account a) {
        String s = "";

       //Translate to pretty account type
        switch(a.getAccountType()){
            case Account.CHECKING:
                s += "Checking Account\n";
                break;
            case Account.SAVINGS:
                s += "Savings Account\n";
                break;
            case Account.MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.getTransactions()) {
            double amount = t.getAmount();
        	String transactionType = (amount < 0 ? "withdrawal" : "deposit");
            s += "  " + transactionType + " " + toDollars(amount) + "\n";
            total += amount;
        }
        s += "Total " + toDollars(total);
        return s;
    }
    
    public void transferBetweenAccounts(Account fromAccount, Account toAccount, double amount) {
    	if (accounts.contains(fromAccount)) {
    		if (accounts.contains(toAccount)) {
    			try {
    			    fromAccount.withdraw(amount);
    			    toAccount.deposit(amount);
    			} catch (IllegalArgumentException e) {
    				e.printStackTrace();
    			}
    		}
    	}
    }

    private String toDollars(double d) {
        return String.format("$%,.2f", abs(d));
    }
}
