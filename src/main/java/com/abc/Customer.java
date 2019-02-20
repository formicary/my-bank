package com.abc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Customer {
	
    private String fullName;
    private List<Account> accounts = new ArrayList<Account>();

    // Constructor to create a Customer and open MULTIPLE Accounts
    public Customer(String fullName, List<Account> accounts) {
        this.fullName = fullName;
        for (Account a : accounts) {
        	openAccount(a);
        }
    }
    // Constructor to create a Customer and open ONE Account
    public Customer(String fullName, Account account) {
        this.fullName = fullName;
        openAccount(account);
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
    	this.fullName = fullName;
    }
    
    // Utility Methods
    
    public void openAccount(Account account) {
        accounts.add(account);
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public HashMap<String, Double> totalInterestEarned() {
    	HashMap<String, Double> currencyAmounts = new HashMap<String, Double>();
        for (Account a : accounts) {
        	currencyAmounts = manageCurrencies(currencyAmounts, a.getInterestEarned(), a);
        }
        return currencyAmounts;
    }
    
    // Statement showing transactions and totals for each of the Customer's accounts
    public String getStatementForAllAccounts() {
    	HashMap<String, Double> currencyAmounts = new HashMap<String, Double>();
    	String statement = "Statement for " + fullName + "\n";
        for (Account a : accounts) {
            statement += a.getStatement();
            currencyAmounts = manageCurrencies(currencyAmounts, a.getTotalAmount(), a);
        }
        statement += "\nTotal In All Accounts:\n";
        for(Entry<String, Double> e : currencyAmounts.entrySet()) {
        	statement += "- " + e.getKey() + e.getValue() + "\n";
        }
        return statement;
    }
    
    // Manage different Account currencies owned by a single Customer 
    private HashMap<String, Double> manageCurrencies(HashMap<String, Double> currencyAmounts, 
    		double total, Account a){
    	// if currency already exists
		if (currencyAmounts.get(a.getCurrencySymbol()) != null) {
			total += currencyAmounts.get(a.getCurrencySymbol());
		}
		// add total amount into map that contains associated currencies 
		currencyAmounts.put(a.getCurrencySymbol(), total);
		return currencyAmounts;
    }
    
    public String getInformationOnCustomerAccounts() {
    	String str = "";
    	for (Account a : accounts) {
    		str += a.getAccountInformation();
    	}
    	return str;
    }
    
    // A customer can transfer between his accounts using account IDs
    public void transferBetweenTwoAccounts(double amount, int senderId, int destinationId) 
    		throws IllegalArgumentException, UnsupportedOperationException {
    	Account sender = getAccountById(senderId);
    	Account destination = getAccountById(destinationId);
    	transferBetweenTwoAccounts(amount, sender, destination);
    } 
    // A customer can transfer between his accounts using Account Objects
    public void transferBetweenTwoAccounts(double amount, Account sender, Account dest)
    		throws IllegalArgumentException, UnsupportedOperationException {
    	if (sender.equals(dest)) {
    		throw new UnsupportedOperationException(
					"Sender Account must be different than Destination Account.");
    	}
    	try {
    		sender.withdraw(amount);
    		dest.deposit(amount);
    	} catch(NullPointerException e) {
    		e.printStackTrace();
    	}
    }
    
    public Account getAccountById(int id) {
    	for (Account a : accounts) {
    		if (a.getId() == id) {
    			return a;
    		}
    	}
		return null;
    }
    
}
