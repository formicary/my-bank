package com.accenture;

import com.accenture.accounts.Account;

import java.util.ArrayList;
import java.util.List;

public final class Bank {

    // A constant that is used for daily interest calculations;
    public static final int DAYS_IN_YEAR = 365;

    private List<Customer> customers = new ArrayList<>();

    public int getTotalCustomers() {
        return customers.size();
    }

    public long getNumberOfOpenAccounts() {
        return customers.stream().map(Customer::getNumberOfAccounts).count();
    }

    public void addCustomer(Customer customer) {

        boolean customerAlreadyExists = this.customers.stream()
                .anyMatch(customer1 -> customer1.getId().equals(customer.getId()));

        if (customerAlreadyExists) {
            throw new IllegalArgumentException("Customer Already Exists");
        } else {
            this.customers.add(customer);
        }
    }

    public void deleteCustomer(Customer customer) {
        int index = customers.indexOf(customer);
        if (index == -1) {
            throw new IllegalArgumentException("Customer does not exist in back");
        } else {
            customers.remove(index);
        }
    }

    public String customerSummary() {
        StringBuilder summary = new StringBuilder("Customer Summary");
        customers.forEach(customer -> {
            summary.append("\n - ").append(customer);
        });

        return summary.toString();
    }

    public DollarAmount totalInterestPaid() {
        return customers.stream()
                .map(Customer::getTotalInterestPaidToCustomer)
                .reduce(DollarAmount.fromInt(0), DollarAmount::plus);
    }

    public void applyDailyInterest() {
        customers.stream().map(Customer::getAccounts).flatMap(List::stream).forEach(Account::applyDailyInterest);
    }


    @Override
    public String toString() {
        return "Bank with " + customers.size() + " customers and " + getNumberOfOpenAccounts() + " currently open accounts";
    }
}
