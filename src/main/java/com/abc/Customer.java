package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

import java.math.BigDecimal;
import java.text.DecimalFormat;

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
    
    public List<Account> getAccounts(){
    	
    	return accounts;
    }

    public BigDecimal totalInterestEarned() {
        
    	BigDecimal total = new BigDecimal(0.0);
        for (Account a : accounts)
            total = total.add(a.interestEarned());
        
        return total;
    }

    public String getStatement() {
    	
    	BigDecimal total = new BigDecimal(0.0);
        String statement = null;
        statement = "Statement for " + name + "\n";
        
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total = total.add(a.getBalance());
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        
        return statement;
    }

    private String statementForAccount(Account a) {
        
    	String s = a.getAccountType()+"\n";       

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
    
    private String toDollars(BigDecimal d){
    	
    	DecimalFormat df = new DecimalFormat("#,###.00");
    	return "$"+df.format(d.abs());
    }
    
    public void makeTransfer(Account fromAccount, Account toAccount, double amount) {
    	
    	if (!accounts.contains(fromAccount) || !accounts.contains(toAccount)) {
    		
    		throw new IllegalArgumentException("Accounts must belong to customer");
    	}
    	else {
    		
    		fromAccount.withdraw(amount);
        	toAccount.deposit(amount);
    	}
    }
    
    public BigDecimal compoundAccountsDaily() {
    	
    	BigDecimal total = new BigDecimal(0.0);
    	for (Account a : accounts)
    		total = total.add(a.compoundDailyInterest());
    	
    	return total;
    }
}
