package com.abc;

import com.abc.Exceptions.CustomerNameAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;

public class Bank {

    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();
    }

    /**
     * Adds a customer to the bank if there is not a customer with a duplicate
     * name already in the list.
     * 
     * @param customer
     * @throws com.abc.Exceptions.CustomerNameAlreadyExistsException
     */
    public void addCustomer(Customer customer)
            throws CustomerNameAlreadyExistsException {
        
        boolean nameExists = customers.stream()
                                      .anyMatch(cust -> customer.getName().equals(cust.getName()));
        if (nameExists) {
            throw new CustomerNameAlreadyExistsException();
        } else {
            customers.add(customer);
        }
    }

    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers) {
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        }
        return summary;
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }


    public double totalInterestPaid() {
        double total = 0;
        for (Customer c : customers) {
            total += c.totalInterestEarned();
        }
        return total;
    }

    /**
     * Checks if bank contains any customers, if so, returns the first in the
     * list.
     * 
     * @return String Either the first customers name, or "Bank has no customers"
     */
    public String getFirstCustomer() {

        if (customers.size() > 0) {
            return customers.get(0).getName();
        } else {
            return "Bank has no customers";
        }
    }
    
    public int numberOfCustomers(){
        return customers.size();
    }

    /**
     * If customer exists within bank, remove
     * 
     * @param name
     * @return boolean true, if customer was removed, false otherwise.
     */
    public boolean removeCustomer(String name) {
        boolean customerExists = customers.stream().anyMatch(
                cust -> name.equals(cust.getName()));

        if (customerExists) {
            customers.removeIf(cust -> name.equals(cust.getName()));

            return true;
        } else {
            return false;
        }
    }

}
