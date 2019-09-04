package com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 * Bank class to imitate a bank which has a list of Customers who have Accounts.
 * Can get a Customer Summary of containing each customer and their accounts and
 * an Interest Summary containg interest gained per customer/account
 * 
 * @author Accentric
 * @author Liam Challis
 *
 */
public class Bank {
	
	 private List<Customer> customers;

	 /**
	  * Constructor which initializes the list of Customers
	  */
	 public Bank() {
		 customers = new ArrayList<Customer>();
	 }

	 /**
	  * Method which adds a pre-existing Customer to the Bank's list of Customers
	  * @param customer Object to be added to the list
	  */
	 public void addCustomer(Customer customer) {
		 customers.add(customer);
	 }
	 
	 /**
	  * Method that produces a String listing each Customer and the number of accounts their own
	  * @return Debug String for the list of Customers the bank has
	  */
	 public String customerSummary() {
	        String summary = "Customer Summary";
	        for (Customer c : customers)
	            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
	        return summary;
	 }
	 
	 //Make sure correct plural of word is created based on the number passed in:
	 //If number passed in is 1 just return the word otherwise add an 's' at the end
	 private String format(int number, String word) {
		 //assumes 0 accounts is the correct pronouc instead of 0 account
		 return number + " " + (number == 1 ? word : word + "s");
	 }
	 
	 //Gets the total of interest paid in all accounts by all customers
	 private double totalInterestPaid() {
		 double total = 0;
		 for(Customer c: customers)
			 total += c.totalInterestEarned();
		 return total;
	 }
	 
	 /**
	  * Method that produces a String listing each Customer, their accounts and interest earned
	  * by the account, as well as totals per Customer and overall
	  * @return Debug String for all Accounts interest earnings
	  */
	 public String interestSummary() {
		 String interestSummary = "Interest Summary";
		 for (Customer customer : customers) { //for every Customer
			 interestSummary += "\n - " + customer.getName() + " $" + customer.totalInterestEarned(); //get name + their total
			 for (Account account : customer.getAccounts()) { //for every account
				 interestSummary += "\n  " + account.getAccountType() + " " + account.interestEarned; //get type + it's total
			 }
			 interestSummary += "\n";
		 }
		 interestSummary += "\n";
		 interestSummary += ("Total Interest $" + totalInterestPaid()); //record total overall earned
		 return interestSummary;
	 }
	 
	 /**
	  * Method that would be called every 24hr that signals each account to earn interest based on their
	  * current amount and account type
	  */
	 public void compoundDailyInterest() {
		 for (Customer customer : customers) {
			 for (Account account : customer.getAccounts()) {
				 account.interestEarned();
			 }
		 }
	 }
	 
	 /**
	  * Debug method that returns the name in String format of the first customer of the banks list
	  * @return
	  */
	 public String getFirstCustomer() {
	        try {
	            customers = null;
	            return customers.get(0).getName();
	        } catch (Exception e){
	            e.printStackTrace();
	            return "Error";
	        }
	    }

}
