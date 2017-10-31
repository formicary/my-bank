package com.abc;

//Imports
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
	//Variables to initialise the name of the customer and the accounts that they possess
	private String name;
    private List<Account> accounts;

    //Constructor
    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    //Name getter
    public String getName() {
        return name;
    }

    //Method for a customer to open an account
    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    //Getter for the number of accounts a customer has
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    //Method to return the interest accrued from all the accounts they possess, accumulated
    public double totalInterestEarned() {
        double total = 0.0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    //Method to return a statement for a customer pertaining to each of their accounts
    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    //Child method for above method
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
        for (Transaction t : a.transactions) {
            s += "  " + (t.amount < 0.0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        s += "Total " + toDollars(total);
        return s;
    }

    //Method to convert a double value into dollars
    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
    
    //Method for a customer to transfer an amount from one of their accounts, into another of their accounts
    public void transferBetweenAccounts(Account a, Account b, double amount) {
    	for(Account account : accounts) {
    		if(a == account) {
    			for(Account account2 : accounts) {
    				if(b == account2 && a != b) {
    					if(amount > 0.0) {
    				    	a.withdraw(amount);
    				    	b.deposit(amount);
    			    	}
    				}
    			}
    			break;
    		}
    	}
    }
}
