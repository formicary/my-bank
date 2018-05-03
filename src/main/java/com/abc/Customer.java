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

    public Customer openAccount(Account account, Bank bank) {
    		if(!bank.checkifCustomerExist(account.getAccountHolder())) throw new IllegalArgumentException("The account holder is not a customer of the bank"); 
    		
        if(name != account.getAccountHolder()) {
        		throw new IllegalArgumentException("The account holder name does not match"); 
        }else accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts) {
        		a.updateBalance();
        		total += a.getinterest();
        }
            
        return total;
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.getAccountBalance();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    private String statementForAccount(Account a) {
        String s = "";

       //Translate to pretty account type
        s += a.get_accountType()+"\n";

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.getTransactions()) {
            s += "  " + (t.getamount() < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.getamount()) + "\n";
            total += t.getamount();
        }
        s += "  interest " + toDollars(a.getinterest())+"\n"; 
        total += a.getinterest();
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
    
    private boolean account_exist(Account account) {
    		boolean exist = false;
    		for(int i = 0;i<accounts.size();i++) {
    			if(accounts.get(i) == account) exist = true;
    		}
    		
    		if (exist) return true;
    		else return false;
    }
    
    public boolean transfer(double amount, Account from_account, Account to_account) {
    		
    		if (name != from_account.getAccountHolder() || name!= to_account.getAccountHolder()) return false;
    		
    		if(account_exist(from_account) && account_exist(to_account)) {
    			if(from_account.withdraw(amount)) {
    				to_account.deposit(amount);
        			return true;
    			} else return false;
    		} else return false;
    		
		
    }
}
