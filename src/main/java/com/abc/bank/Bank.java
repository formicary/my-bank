package com.abc.bank;

import com.abc.account.Account;
import com.abc.account.AccountFactory;
import com.abc.account.AccountType;
import com.abc.customer.Customer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bank {
    private final List<Customer> customers;

    public Bank() {
        customers = new ArrayList<>();
    }

    public void addCustomer(Customer customer) {
        if (customer != null && !customers.contains(customer)) {
            customers.add(customer);
            customer.setBank(this);
        }
    }

    public String createCustomerSummary() {
        StringBuilder summary = new StringBuilder("Customer Summary");
        for (Customer customer : customers) {
            int numberOfAccounts = customer.getNumberOfAccounts();
            String pluralFormat = pluralFormat(numberOfAccounts, " account");
            String customerSummary = String.format("\n - %s (%s%s)",
                    customer.getName(),
                    numberOfAccounts,
                    pluralFormat);
            summary.append(customerSummary);
        }
        return summary.toString();
    }

    private String pluralFormat(int number, String word) {
        return number == 1 ? word : word + "s";
    }

    public double totalInterestPaid() {
        double total = 0;
        for (Customer customer : customers)
            total += customer.totalInterestEarned();
        return total;
    }

    public Account createAccount(Customer customer, AccountType accountType) {
        return AccountFactory.create(customer, accountType);
    }

    public List<Customer> getCustomers() {
        return Collections.unmodifiableList(customers);
    }
}
