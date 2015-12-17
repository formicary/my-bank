package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
  private List<Customer> customers;

  public Bank() {
    customers = new ArrayList<Customer>();
  }

  public void addCustomer(Customer customer) {
    customers.add(customer);
  }

  // TODO: should this include a number of accounts?
  // Prints a summary of customers' accounts
  public String customerSummary() {
    String summary = "Customer Summary";
    for (Customer c : customers) {
      summary += "\n - " + c.getName() + " ("
          + pluralise(c.getNumberOfAccounts(), "account") + ")";
    }
    return summary;
  }

  // Make sure correct plural of word is created based on the number passed in:
  // If number passed in is 1 just return the word otherwise add an 's' at the
  // end
  private String pluralise(int number, String word) {
    return number + " " + (number == 1 ? word : word + "s");
  }

  // Calculates the total interest to be paid to the customer
  public double totalInterestPaid() {
    double total = 0.0;
    for (Customer c : customers) {
      total += c.totalInterestEarned();
    }
    return total;
  }

  // TODO: error here, customers should not be set to null
  public String getFirstCustomer() {
    try {
      customers = null;
      return customers.get(0).getName();
    } catch (Exception e) {
      e.printStackTrace();
      return "Error";
    }
  }
}
