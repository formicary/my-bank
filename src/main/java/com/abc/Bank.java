package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;


    public Bank() {
        customers = new ArrayList<Customer>();
    }

    public void addCustomer(Customer customer) {

        if (customers.contains(customer)) {
            throw new UnsupportedOperationException("The customer is already in the bank register");
        } else {
            customers.add(customer);
        }
    }

    public String customerSummary() {
        String summary = "Customer Summary\n";
        summary += Customer.makeSymbolLine('-', 25);

        for (Customer c : customers)
            summary += "  * " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")\n";

        return summary;
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public String totalInterestPaid() {
        String report = "Interest Paid Summary\n";

        report += Customer.makeSymbolLine('-', 30);

        double total = 0D;

        for (Customer customer : customers) {
            double earnedInterest = getCustomerPaidInterest(customer);

            report += "  * Interest paid to " + customer.getName() + " - "
                    + "$" + Account.decimalFormatter.format(earnedInterest) + "\n";
        }

        report += Customer.makeSymbolLine('-', 30);
        report += "Total Interest Paid: " + "$" + getTotalInterestPaid() + "\n";

        return report;
    }

    public Customer getFirstCustomer() {
        try {
            return customers.get(0);
        } catch (Exception e) {
            throw new IndexOutOfBoundsException("The customer is not registered in the bank");
        }
    }

    // Calculates the total interest paid by the bank
    public double getTotalInterestPaid() {
        double total = 0D;

        for (Customer customer : customers)
            total += customer.getTotalInterestEarned();

        return Double.parseDouble(Account.decimalFormatter.format(total));
    }


    public int getNumberOfCustomers() {
        return customers.size();
    }

    public double getCustomerPaidInterest(Customer customer) {
        return customer.getTotalInterestEarned();
    }
}
