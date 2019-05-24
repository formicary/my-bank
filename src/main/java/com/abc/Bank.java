package com.abc;

import com.abc.util.Money;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Bank {

    private Collection<Customer> customers = Collections.synchronizedCollection(new ArrayList<>());

    public void addCustomer(Customer customer)  {

        boolean customerAlreadyExists = this.customers.stream()
                .anyMatch(customer1 -> customer1.getID().equals(customer.getID()));

        if (customerAlreadyExists) {
            throw new IllegalArgumentException("Customer Already Exists");
        }
        else {
            this.customers.add(customer);
        }
    }

    public void deleteCustomer(Customer customer) {

    }

    public String customerSummary() {
        StringBuilder summary = new StringBuilder("Customer Summary");
        for (Customer c : customers)
            summary.append("\n - ").append(c.toString());
        return summary.toString();
    }

    public Money totalInterestPaid() {
        Money totalInterestPaid = new Money(BigDecimal.ZERO);
        for(Customer c: customers)
            totalInterestPaid = totalInterestPaid.plus(c.totalInterestEarned());
        return totalInterestPaid;
    }
}
