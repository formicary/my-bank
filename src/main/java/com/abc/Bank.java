package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a class for Bank. It contains a list of customers.
 * @author Peng Shao. Modifed based on the exercise provided by Accenture.
 * @version  03/05/2018
 */
public class Bank {

    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();
    }

    /**
     * A method for adding new customer.
     * @param customer A customer of the bank
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * A method for returning the customer's summary
     * @return Customer's summary.
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
        return number + " " + (number == 1 ? word : word + "s");
    }

    /**
     * This method works out hte total amount of interest paid for all the customers.
     * @return The total amount of interest paid.
     */
    public BigDecimal totalInterestPaid() {
        BigDecimal total = new BigDecimal(0);
        for(Customer c: customers)
            total = total.add(c.totalInterestEarned());
        return total.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    /**
     * This method gets the first customer of the bank.
     * @return The first customer of the bank.
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
