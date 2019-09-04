package com.abc;

import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.abs;

/**
 * Customer class where each Customer has a name and a possible list of Accounts belonging to them
 * 
 * @author Accenture
 * @author Liam
 *
 */
public class Customer {
	
	 private String name;
	 private List<Account> accounts;
	 
	 /**
	  * Constructor which takes in the Customers name and initalizes the Accounts list
	  * @param name String which contains the name of the new Customer
	  */
	 public Customer(String name) {
		 this.name = name;
		 this.accounts = new ArrayList<Account>();
	 }
	 
	 
	 /**
	  * Getter for the name attribute
	  * @return name Attribute belonging to the Customer
	  */
	 public String getName() {
		 return name;
	 }
	 
	 /**
	  * Setter for the name attribute, incase the Customer wishes to change their name
	  * @param newName The String containing the new name
	  */
	 public void setName(String newName) {
		 name = newName;
	 }
	 
	 /**
	  * Method that creates a new CheckingAccount and adds it to the Customer's account list
	  */
	 public void openCheckingAccount() {
		 accounts.add(new CheckingAccount(this));
	 }
	 
	 
	 /**
	  * Method that creates a new SavingAccount and adds it to the Customer's account list
	  */
	 public void openSavingAccount() {
		 accounts.add(new SavingAccount(this));
	 }
	 
	 
	 /**
	  * Method that creates a new MaxiSavingAccount and adds it to the Customer's account list
	  */
	 public void openMaxiSavingAccount() {
		 accounts.add(new MaxiSavingAccount(this));
	 }
	 
	 
	 /**
	  * Method that returns the number of Accounts the Customer currently has
	  * return Number of accounts in accounts attribute
	  */
	 public int getNumberOfAccounts() {
		 return accounts.size();
	 }
	 
	 
	/**
	 * Method that calculates and returns the total interest the customer has earned through all their accounts
	 * @return Value that the customer has gained through interest rates
	 */
	 public double totalInterestEarned() {
		 double totalInterest = 0.0;
		 for (Account account : accounts) {
			 totalInterest += account.interestEarned; 
		 }
		 return totalInterest;
	 }
	 
	 //Generate a String that provides debug information for a single account
	 private String statementForAccount(Account account) {
		 String statement = "";
		 statement += account.getAccountType() + "\n";
		 for (Transaction t : account.transactions) { //for every transaction the account has, add the information to string
			 statement += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount, false) + "\n";
		 }
		 statement += "Total " + toDollars(account.amount, true);
	     return statement;
	 }
	 
	 /**
	  * Method that generates a Customers Statement that contains their name, each account they own and
	  * the total amount present in each account + total amount 
	  * @return Debug String containing Customer Account information
	  */
	 public String getStatement() {
	        String statement = null;
	        statement = "Statement for " + name + "\n";
	        double total = 0.0;
	        for (Account account : accounts) {
	            statement += "\n" + statementForAccount(account) + "\n";
	            total += account.amount;
	        }
	        statement += "\nTotal In All Accounts " + toDollars(total, true);
	        return statement;
	    }
	 
	 //Method that formats the amount values to 2S.F then adds $ to the front
	 //For total values will include either +/-, for transaction amounts will only include the value
	 private String toDollars(double d, boolean totalAmount){
		 if (totalAmount) {
			 return String.format("$%,.2f", d);
		 } else {
			 return String.format("$%,.2f", abs(d));
		 }
	        
	 }
	 
	 /**
	  * Getter for the accounts list attribute
	  * @return accounts list attribute
	  */
	 public List<Account> getAccounts() {
		 return accounts;
	 }
	 
	 


}
