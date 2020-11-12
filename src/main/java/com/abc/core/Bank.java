package com.abc.core;

import lombok.NoArgsConstructor;
import com.abc.utils.BankUtils;

import java.util.ArrayList;
import java.util.List;

import static com.abc.utils.BankUtils.CUSTOMER_SUMMARY;

@NoArgsConstructor
public class Bank {

    private final List<Customer> customers = new ArrayList<>();

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public String summaryOfAllCustomers() {
        // TODO: change text if there are no customers
        return customers.stream()
                .map(BankUtils::formatCustomer)
                .collect(
                        () -> new StringBuilder(CUSTOMER_SUMMARY),
                        StringBuilder::append,
                        StringBuilder::append)
                .toString();
    }

    public double totalInterestPaid() {
        return customers.stream()
                .map(Customer::totalInterestEarned)
                .reduce(0.0, Double::sum);
    }

}
