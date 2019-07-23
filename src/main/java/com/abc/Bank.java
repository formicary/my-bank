package com.abc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Bank {

    private final List<Customer> customers;

    public Bank() {
        customers = new ArrayList<>();
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public String customerSummary() {
        String summary = "Customer Summary";
        for (Customer c : customers)
            summary += "\n - " + c.getName() + " (" + format(c.getNumberOfAccounts(), "account") + ")";
        return summary;
    }

    //Make sure correct plural of word is created based on the number passed in:
    //If number passed in is 1 just return the word otherwise add an 's' at the end
    private String format(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public double totalInterestPaid() {
        return customers.stream().mapToDouble(Customer::totalInterestEarned).sum();
    }

    // Method should be triggered by job scheduler every year
    public void addInterestRatePerAnnum(Customer customer) {
        customer.getAccounts().forEach(account -> account.deposit(account.getInterestEarned()));
    }

    public void addInterestRateDaily(Customer customer) {
        customer.getAccounts().forEach(account -> {

            LocalDate lastTransactionDate;
            List<Transaction> transactions = account.transactions;

            if (transactions == null || transactions.isEmpty()) {
                throw new IllegalArgumentException("No transactions.");
            } else {
                lastTransactionDate = transactions.get(transactions.size() - 1).transactionDate;
            }

            while (lastTransactionDate.isBefore(LocalDate.now())) {
                account.deposit(account.getInterestEarned() / 360);
                lastTransactionDate = lastTransactionDate.plusDays(1L);
            }
        });
    }
}
