package com.abc;
 
import java.util.*;

import static java.lang.Math.abs;

import java.text.DecimalFormat;

public class Customer {
	// Name and list of accounts
    private String name;
    private List<Account> accounts;
    
    // initialization 
    protected Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }
    
    // getter method used in 
    public String getName() {
        return name;
    }
    
    // Open a new account
    protected Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }
    
    // to get the number of accounts
    protected int getNumberOfAccounts() {
        return accounts.size();
    }
    
    // to check if an account e, exists in the list of accounts
    protected boolean accountExists(Account e) {
    	for(Account a : accounts)
    		if(a.getAccountType() == e.getAccountType())
    			return true;
    	return false;
    }
    
    // Total Interests earned on all accounts 
    protected double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return roundTo2Decimal(total);
    }
    
    protected double roundTo2Decimal(double amount) {
    	DecimalFormat df = new DecimalFormat("#.##"); 
        return Double.valueOf(df.format(amount));
    }
    
    
    // Transfer amount 'amt' from account 'from' to account 'to'
    protected boolean transfer(double amt, Account from, Account to) {
    	try {
    		// only if accounts are more than 1 and both accounts exist
    		if(getNumberOfAccounts() > 1 && accountExists(from) && accountExists(to)) {
    			
    			// if withdraw is successful then only deposit
    			if(from.withdraw(amt)) {
    				to.deposit(amt);
    				return true;
    			}
    			return false; // when withdraw fails
    		}
    		else 
    			throw new Exception("Invalid Transfer. Account/s do not exist");
    	}
       	catch(Exception e) {
       		System.out.println(e);
       		return false;
       	}
    }
    
    // to get a statement to show transactions and total for all accounts
    protected String getStatement() {
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
    
    protected String statementForAccount(Account a) {
        String s = "";

       // Translate to pretty account type
        switch(a.getAccountType()){
            case CHECKING:
                s += "Checking Account\n";
                break;
            case SAVINGS:
                s += "Savings Account\n";
                break;
            case MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }

        // Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {
        	if(t.getTransactionType() == TransactionType.ADDEDINTEREST) 
        		s += "  interest " + toDollars(t.getAmount()) + "\n";
        	else
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
