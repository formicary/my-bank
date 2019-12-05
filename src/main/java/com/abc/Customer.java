package com.abc;

import java.util.LinkedHashMap ;

public class Customer {
    private String name;
    private LinkedHashMap<String, Account> accounts;	// accounts have id mappings for easier access and to keep them private to the customer

    public Customer(String name) {
        this.name = name;
        this.accounts = new LinkedHashMap <String, Account>();
    }

    public String getName() { return name; }
    public int getNumberOfAccounts() { return accounts.size(); }
    public double getAccountBalance(String id) { return accounts.get(id).getBalance(); }
    

    public String openAccount(Account.Type type) {
    	String id = ""+accounts.size();		// id is just an incremental value for simplicity, but can be changed to UUID
        accounts.put(id, new Account(type));
    	
    	return id;
    }
    
    public void deposit(String accountID, double amount) {
    	if (!accounts.containsKey(accountID)) throw new IllegalArgumentException("tryint to deposit to non-existent account");
    	accounts.get(accountID).deposit(amount);
    }
    
    public void withdraw(String accountID, double amount) {
    	if (!accounts.containsKey(accountID)) throw new IllegalArgumentException("tryint to withdraw from non-existent account");
    	accounts.get(accountID).withdraw(amount);
    }
    
    public void transfer(String fromID, String toID, double amount) {
    	if (!accounts.containsKey(fromID) || !accounts.containsKey(toID)) { 
    		throw new IllegalArgumentException("tryint to transfer to/from non-existent account");
    	}
    	accounts.get(fromID).transfer(-amount, toID);
    	accounts.get(toID).transfer(amount, fromID);
    }

    // updates all accounts and returns the daily interest earned  
    public double earnDailyInterest() {
        double total = 0;
        for (Account a : accounts.values()) {
            total += a.earnInterest();
        }
        return twoDecimal(total);
    }
    
    // returns the total interest earned from the beginning
    public double getTotalInterestEarned() {
    	 double total = 0;
         for (Account a : accounts.values()) {
             total += a.getTotalInterestEarned();
         }
         return twoDecimal(total);
    }
    
    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        
        double total = 0.0;
        for (String a : accounts.keySet()) {
        	statement += "\n" + "Account " + a + "\n";
            statement += accounts.get(a).getStatement() + "\n";
            total += accounts.get(a).getBalance();
        }
        statement += "\nTotal In All Accounts " + String.format("$%,.2f",total);
        return statement;
    }
    
    // helper method to get 2 decimal precision
    private double twoDecimal(double n) {
    	return Double.parseDouble(String.format("%.2f", n)); 
    }
  
}
