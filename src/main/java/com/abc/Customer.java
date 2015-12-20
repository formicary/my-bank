package com.abc;

import java.util.ArrayList;
import java.util.Date;
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
    
    public List<Account> getAccounts() {
        return accounts;
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
    

    public double InterestEarnedPerDay(Date d) {
   	        double total = 0;
   	       for (Account a : accounts)
   	            total += a.interestPerDay(d);
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
        	 	               statement += "\n" + statementForAccount(a) + "\n";
        	 	               total += a.sumTransactions();
        	 	           }
        	 	           statement += "\nTotal In All Accounts " + toDollars(total);
        	          }
        return statement;
    }

    private String statementForAccount(Account a) {
        String s = "";

       //Translate to pretty account type
        AccountType accountType = a.getAccountType();
        if (AccountType.CHECKING.equals(accountType)) {
        	s += "Checking Account\n";
        }
        else if(AccountType.SAVINGS.equals(accountType)) {
        	s += "Savings Account\n";
        }
        else{
        	s += "Maxi Savings Account\n";
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
    
    public void accountTransfer (Account acOne, Account acTwo, double amount) {
    	  if (! accounts.contains(acOne) || ! accounts.contains(acTwo)) {
    	   throw new IllegalArgumentException("Accounts must be owned by customer!");
    	  } else {
    		  if(amount>0){
    			  acOne.withdraw(amount);
    	    	  acTwo.deposit(amount);
    		  }
    	   
    	  }
    	 }
    
    public boolean ChangeAccountType(AccountType a,Account acc)
    {
    	if(acc.getAccountType() == a || ! accounts.contains(acc) ){
    		return false;
    	}
    	else{
    		acc.setAccountType(a);
    		return true;
    	}
    }
}
