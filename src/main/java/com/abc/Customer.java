package com.abc;

import java.util.*;
import static java.lang.Math.abs;

import java.text.SimpleDateFormat;

public class Customer implements ICustomer {
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
    
    //Get the sum of the total interest earned for all accounts
    public double sumInterestEarned() {
        double total = 0;
        for (IAccount a : accounts)
            total += a.totalinterestEarned();
        return total;
    }

    //Return the statement for all accounts, each account is labelled with an account number.
    public String getStatement() {
        String statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (int i = 0; i < accounts.size(); i++) {
        	IAccount a = accounts.get(i);
            statement += "\nAccountNo."+(i+1)+" - "+statementForAccount(a) +"\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }
    
    //Get the string of all transactions of an account
    private String statementForAccount(IAccount a) {
        String s = a.getAccountType()+" Account\n";
        double total = 0.0;
        
        for (ITransaction t : a.getTransactions()) {
        	s += printTransaction(t);
            total += t.getTransactionAmount();
        }
        s += "Total " + toDollars(total);
        return s;
    }
    
    //Get the string of each individual transaction of an account, includes the date
    private String printTransaction(ITransaction transaction){
    	double amount = transaction.getTransactionAmount();
    	Calendar date = transaction.getTransactionDate();
    	SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    	String output = "  "+typeofTransaction(amount)+" "+toDollars(amount)+" - "+sdf.format(date.getTime())+"\n";
    	return output;
    }
    
    //Get the type of transaction in the account
    private String typeofTransaction(double amount){
    	if (amount < 0){
    		return "withdrawal";
    	}
    	else{
    		return "deposit";
    	}
    }
    
    //Add dollars signs and round up to 2 decimal points.
    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
    
    /*Transfer money between two accounts opened by a customer, 
     * each account can be referenced by the account number shown in the statement
     * To transfer, it will withdraw money from one account and deposit the money to 
     * the other account.  
    */
    public void internalTransfer(int fromAccountNo, int toAccountNo, double amount){
    	IAccount a = accounts.get(fromAccountNo-1);
    	IAccount b = accounts.get(toAccountNo-1);
    	a.withdraw(amount);
    	b.deposit(amount);	
    }
    
}
