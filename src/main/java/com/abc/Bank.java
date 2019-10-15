package com.abc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * The Bank class holds all the information regarding its Customers.
 */
public class Bank {
    private List<Customer> customers;

    /**
     * Initializes a new Bank with no Customers.
     */
    public Bank() {
        customers = new ArrayList<>();
    }

    /**
     * Add a new Customer to the Bank.
     *
     * @param customer new customer object
     */
    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    /**
     * List all customers and the amount of accounts they have opened.
     *
     * @return list of customer information or empty string if no customers
     */
    public String customerSummary() {
        if (customers == null) return "";
        if (customers.isEmpty()) return "No Customers";
        StringBuilder summary = new StringBuilder();
        summary.append("Customer Summary");
        for (Customer c : customers) {
//            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
            summary.append("\n - ")
                    .append(c.getName())
                    .append(" (")
                    .append(format(c.getNumberOfAccounts(), "account"))
                    .append(")");
        }
//        System.out.println(summary.toString());
        return summary.toString();
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    /**
     * Total interest paid to all customers.
     *
     * @return total interest paid out
     */
    public BigDecimal totalInterestPaid() {
        BigDecimal total = BigDecimal.ZERO;
        for (Customer c : customers) {
            total = total.add(c.totalInterestEarned());
        }
//        System.out.println("total interest paid to all customers: " + total);
        return total;
    }
}
