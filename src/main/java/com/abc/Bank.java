package com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the object for the Bank.
 * It contains an ArrayList of the customers.
 * @author Matthew Howard - <a href="mailto:m.o.howard@outlook.com">m.o.howard@outlook.com</a>
 */

public class Bank {
 private List < Customer > customers;

 /**
  * This is the constructor it initialises the ArrayList of customers to be empty
  */
 public Bank() {
  customers = new ArrayList < Customer > ();
 }

 /**
  * This function takes a Customer object and adds it the LinkedList of Customers that the bank holds
  * @param customer  This is the Customer to be added to the Bank's list of Customers
  */
 public void addCustomer(Customer customer) {
  customers.add(customer);
 }

 /**
  * This function returns a neatly formatted String detailing the Customers at the bank
  * @return  A String containing a neatly formatted summary of all of the customers in the LinkedList
  */
 public String customerSummary() {
  String summary = "Customer Summary";
  for (Customer c: customers) {
   summary += "\n - " + c.getName() + " (" + pluraliseString(c.getNumberOfAccounts(), "account") + ")";
  }
  return summary;
 }

 /**
  * This is a function that works out whether or not to pluralise a given String and returns a correctly formatted version
  * @param number    An integer that will be used when deciding whether to pluralise or not
  * @param word  The string that will be formatted correctly
  * @return  A String containing the correct formatting for pluralisation
  */
 private String pluraliseString(int number, String word) {
  return number + " " + (number == 1 ? word : word + "s");
 }

 /**
  * This function works out the total amount of interest paid out by the bank to all customers across all accounts
  * @return  A double representing the amount of money to be paid in interest by the bank (per annum using the current amounts in the accounts)
  */
 public double totalInterestPaid() {
  double total = 0;
  for (Customer c: customers) {
   total += c.totalInterestEarned();
  }
  return total;
 }

    /**
     * This function will update all the accounts held by all of the Customers at this bank and add their daily interest..
     */
 public void payDailyInterest() {
     for (Customer c : customers) {
         c.addDailyInterest();
     }
 }

}