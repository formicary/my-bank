package com.abc;

import java.text.NumberFormat;
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

    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers)
            summary += "\n - " + c.getFullName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public String totalInterestPaid() {
    	String s = "=== Statement for Total Interest Earned Across all Accounts ===";
        double total = 0.0;
        String totalAsString = "";
        double customerIndividualInterest = 0.0;
        for(Customer c: customers) {
        	customerIndividualInterest = c.totalInterestEarned();
        	total += c.totalInterestEarned();
        	totalAsString = getCurrencyForUnknownLocale(c, total);
        	s += "\n" + c.getFullName()+ ": " + getCurrencyForUnknownLocale(c, customerIndividualInterest);
        }
        s += "\n=== Total Interest Paid Out: " + totalAsString + "\n*N.B. These figures are rounded to 2 decimal places.";
        return s;
    }

    public String getFirstCustomer() {
        try {
            return customers.get(0).getFullName();
        } catch (Exception e){
            e.printStackTrace();
            return "Error";
        }
    }
    
  //get local currency for unknown customer
    public String getCurrencyForUnknownLocale(Customer c, double amount) {
    	String stringAmount;
    	stringAmount = NumberFormat.getCurrencyInstance(c.getLocale()).format(amount);
    	return stringAmount;
    }

}
