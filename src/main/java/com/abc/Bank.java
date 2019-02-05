package com.abc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Bank {
    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<>();
    }

    public void addCustomer(Customer customer) {
        if (customers.contains(customer))
            throw new UnsupportedOperationException("This customer is already in the bank registry");
        else
            customers.add(customer);
    }

    public Customer getFirstCustomer() {
        try {
            return customers.get(0);
        }
        catch (Exception e) {
            throw new IndexOutOfBoundsException("The bank has no registered users");
        }
    }

    // Returns a summary of all customers and the number of account associated with them
    public String showCustomersSummary() {

        String summary = "Customer Summary\n";
        summary += ReportFormatter.makeSymbolLine('-', 25);

        for (Customer c : customers)
            summary += "  * " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")\n";

        return summary;
    }

    // Returns a report of the total interest paid by the bank and how this insteres
    // is distributed among its customers
    public String showPaidInterestReport() {

        String report = "Interest Paid Summary\n";

        report += ReportFormatter.makeSymbolLine('-', 30);

        double total = 0D;

        for (Customer customer : customers) {
            double earnedInterest = getCustomerPaidInterest(customer);

            report += "  * Interest paid to " + customer.getName() + " - "
                    + "$" + ReportFormatter.decimalFormatter.format(earnedInterest) + "\n";
        }

        report += ReportFormatter.makeSymbolLine('-', 30);
        report += "Total Interest Paid: " + "$" + getTotalInterestPaid() + "\n";

        return report;
    }

    // Determines whether the word should be in plural form depending on the quantity argument
    private String format(int quantity, String word) {
        return quantity + " " + (quantity == 1 ? word : word + "s");
    }

    public int getNumberOfCustomers() {
        return customers.size();
    }

    public double getCustomerPaidInterest(Customer customer) {
        return customer.getTotalInterestEarned();
    }

    // Calculates the total interest paid by the bank
    public double getTotalInterestPaid() {
        double total = 0D;

        for (Customer customer : customers)
            total += customer.getTotalInterestEarned();

        return Double.parseDouble(ReportFormatter.decimalFormatter.format(total));
    }
}
