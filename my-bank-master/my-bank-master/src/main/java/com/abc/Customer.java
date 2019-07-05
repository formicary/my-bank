package com.abc;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class Customer {
	//Change List to ArrayList
    private String name;
    private ArrayList<Account> accounts;

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

    //Interest should be based on amount at time of day awarded, awarded same time daily
    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts) {
            total += a.runningInterest();
        }
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
        //Original Total of all accounts returned incorrectly when negative
        if(total >= 0) {
        	statement += "\nTotal In All Accounts " + toDollars(total);
        }
        else {
        	statement += "\nTotal In All Accounts " + "-" + toDollars(total);
        }
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
        for (Transaction t : a.transactions) {
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        //Original line made s always positive, even when account in debt
        if(total >= 0) {
        	s += "Total " + toDollars(total);
        }
        else {
        	s += "Total " + "-" + toDollars(total);
        }
        return s;
    }

    //Specify account to put the money into, and the amount
    public void moveMoney(Account accountTake, Account accountGive, double amount) {
    	//Only allow money to be moved if both accounts belong to the customer
    	if(accounts.contains(accountGive) && accounts.contains(accountTake)) {
    		accountTake.withdraw(amount);
    		accountGive.deposit(amount);
    	}
    	else {
    		throw new IllegalArgumentException("Can only transfer between customers own accounts");
    	}
    }
    
    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
