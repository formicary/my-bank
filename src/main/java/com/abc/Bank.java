package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> allCustomers;

    public Bank() {
        allCustomers = new ArrayList<Customer>();
    }

    public void addCustomer(Customer customer) {
        allCustomers.add(customer);
    }

//    public String customerSummary() {
//        String summary = "Customer Summary";
//        for (Customer c : allCustomers)
//            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
//        return summary;
//    }
//
//    //Make sure correct plural of word is created based on the number passed in:
//    //If number passed in is 1 just return the word otherwise add an 's' at the end
//    private String format(int number, String word) {
//        return number + " " + (number == 1 ? word : word + "s");
//    }


    public BigDecimal totalInterestPaid() {
        BigDecimal total = new BigDecimal(0);
        for (Customer c : allCustomers)
            total = total.add(c.totalInterestEarned());
        return total;
    }

    public String getFirstCustomer() {
        try {
            allCustomers = null;
            return allCustomers.get(0).getName();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    public String getSpecificCustomer(Customer customer) {
        for (Customer c : allCustomers) {
            if (c.equals(customer)) {
                return c.getName();
            }


        }
        return "Customer does not exist.";
    }
}
