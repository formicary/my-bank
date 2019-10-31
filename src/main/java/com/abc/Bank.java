package com.abc;

import java.util.ArrayList;
import java.util.List;

public final class Bank {
    private final List<Customer> customers;
    private final String bankName;

    public Bank(String bankName) {
        this.bankName = bankName;
        this.customers = new ArrayList<>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public String customerSummary() {
        int numberOfCustomers = this.numberOfCustomers();
        StringBuilder summary = new StringBuilder("Customer Summary for ").append(this.bankName)
                        .append("\n").append(numberOfCustomers).append(" Customer").append(format(numberOfCustomers));
        for (Customer c : customers) {
            int numberOfAccounts = c.getNumberOfAccounts();
            summary.append("\n - ").append(c.getName()).append(" (").append(numberOfAccounts)
                    .append(" account").append(format(numberOfAccounts)).append(")");
        }
        return summary.toString();
    }

    private String format(int number) {
        return (number == 1 ? "" : "s");
    }

    public double totalInterestPaid() {
        double total = 0;
        for(Customer c: customers)
            total += c.totalInterestEarned();
        return total;
    }

    public String getBankName() {
        return bankName;
    }
    public int numberOfCustomers(){
        return this.customers.size();
    }
}
