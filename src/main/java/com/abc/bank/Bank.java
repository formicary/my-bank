package com.abc.bank;

import com.abc.customer.Customer;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class Bank {

    @Getter
    private final List<Customer> customers = new ArrayList<>();

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public String summaryOfAllCustomers() {
        return customers.stream()
                .map(BankUtils::formatCustomer)
                .collect(
                        () -> new StringBuilder(BankUtils.CUSTOMER_SUMMARY),
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
