package com.abc;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Bank {
    private Map<Integer, Customer> customers;

    public Bank() {
        customers = new HashMap<>();
    }

    // Adds a customer to the hash map, using their unique ID as a key.
    public void addCustomer(Customer customer) {
        customers.put(customer.getID(), customer);
    }

    public String customerSummary() {
        String summary = "Customer Summary";

        Iterator it = customers.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Customer c = (Customer) pair.getValue();
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
            it.remove(); // avoids a ConcurrentModificationException
        }
        return summary;
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public BigDecimal totalInterestPaid() {
        BigDecimal total = new BigDecimal(0.00);
        Iterator it = customers.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Customer c = (Customer) pair.getValue();
            total = c.totalInterestEarned().add(total);
            it.remove(); // avoids a ConcurrentModificationException
        }
        return total.setScale(2, RoundingMode.HALF_UP);
    }

    public String getFirstCustomer() {
        try {
            return customers.get(0).getName();
        }
        catch (Exception e){
            e.printStackTrace();
            return "Error";
        }
    }
}
