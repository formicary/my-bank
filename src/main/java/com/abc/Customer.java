package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import java.util.Date;


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
        for (Account a : accounts){
        	Date date = new Date(a.openDate.getTime()+365*24*60*60*1000);
            total += a.interestEarned(date);
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
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    private String statementForAccount(Account a) {
    	StringBuilder sb = new StringBulder();
    	for(Transaction t : a.getTransactions())
    		sb.append(t.amount);
    	
    	
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
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
    
    public void transfer(double amount,int i, int j){
    	if (amount <= 0) {
    		throw new IllegalArgumentException("amount must be greater than zero");
    	} 
    	if (accounts.size() < 2){
    		throw new IllegalArgumentException("you must have at least two accounts to make any transfer");
    	}
    	int check1 = 0;
    	int check2 = 0;
    	for(Account a: accounts){
    		if (a.getAccountType() == i)
    			check1 = 1;
   				Account a1 = a;
   			if (a.getAccountType() == j)
   				check2 = 1;
   				Account a2 = a;
   		}
    	if(check1 == 1 & check2 ==1){
    		a1.withdraw(amount);
    		a2.deposit(amount);
    	} else {
    		throw new IllegalArgumentException("Please correctly specify the types the two accounts");
    	}
    }
}

