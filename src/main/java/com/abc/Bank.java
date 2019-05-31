package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;
    private Utility utility = new Utility();

    public Bank() {
        customers = new ArrayList<Customer>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers)
            summary += "\n - " + c.getName() + " (" + utility.formatWordForPlural(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }



    public double totalInterestPaid(Customer.AccountPortfolioVersion accountPortfolioVersion) {
        double total = 0;
        for (Customer c : customers)
            total += c.totalInterestEarned(accountPortfolioVersion);
        return total;
    }

}
