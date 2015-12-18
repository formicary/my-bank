package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
  private List<Customer> customers;

  public Bank() {
    customers = new ArrayList<Customer>();
  }
  
  public String getFirstCustomer() {
    if (customers != null && customers.size() > 0) {
      return customers.get(0).getName();
    } else {
      return "No customers";
    }
  }

  public void addCustomer(Customer customer) {
    customers.add(customer);
  }

  // Prints a summary of customers' accounts
  public String customerSummary() {
    String summary = "Customer Summary";
    for (Customer c : customers) {
      summary += "\n - " + c.getName() + " ("
          + formatCustomer(c.getNumberOfAccounts(), "account") + ")";
    }
    return summary;
  }

  // Makes sure correct plural of word is created based on n
  private String formatCustomer(int n, String word) {
    return n + " " + (n == 1 ? word : word + "s");
  }

  // Calculates the total interest to be paid to the customer
  public double totalInterestPaid() {
    double total = 0.0;
    for (Customer c : customers) {
      total += c.totalInterestEarned();
    }
    return total;
  }
  
}
