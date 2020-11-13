package com.abc.core.bank;

import com.abc.core.customer.Customer;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.abc.core.bank.BankUtils.CUSTOMER_SUMMARY;

@NoArgsConstructor
public class Bank {

    private final List<Customer> customers = new ArrayList<>();

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public String summaryOfAllCustomers() {
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
