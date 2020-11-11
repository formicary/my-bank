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

    public String customerSummary() {
//        String summary = "Customer Summary";        // TODO: change text if there are no customers
//        for (Customer c : customers)
//            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
//        return summary;

//        StringBuilder builder = new StringBuilder("Customer Summary");
//        customers.forEach(customer -> {
//            builder.append(String.format("\n - %s (%s)", customer.getName(), format(customer.getNumberOfAccounts(), "account")));
//        });
//        return builder.toString();


        return customers.stream()
                .map(BankUtils::formatCustomer)
                .collect(
                        () -> new StringBuilder(CUSTOMER_SUMMARY),
                        StringBuilder::append,
                        StringBuilder::append)
                .toString();
    }

    public double totalInterestPaid() {
//        double total = 0;
//        for(Customer c: customers)
//            total += c.totalInterestEarned();
//        return total;

        return customers.stream()
                .map(Customer::totalInterestEarned)
                .reduce(0.0, Double::sum);
    }

}
