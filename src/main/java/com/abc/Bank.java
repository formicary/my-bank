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
    /**
     * A customer summary builder with information about the name and number of accounts of the customers
     * @return String summary
     */
    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers)
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    /**
     * Method to make sure correct plural of word is created based on the number passed in:
     * If number passed in is 1 just return the word otherwise add an 's' at the end
     * @param number
     * @param word
     * @return
     */

    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }
    /**
     * Method to calculate the total interest paid by all customers.
     * @return double total (interest paid)
     */
    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }
    /**
     * Method to return the first customer of the bank.
     * @return
     */
    public String getFirstCustomer() {
        try {
            return customers.get(0).getName();
        } catch (ArrayIndexOutOfBoundsException e){
            return "There are currently no customers in this Bank";
        } catch(Exception e) {
        	 e.printStackTrace();
        	 return " There has been an unknown error with retrieving the first customer.";
        }
    }
}
