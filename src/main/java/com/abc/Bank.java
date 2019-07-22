package com.abc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;

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

    public void addFlatRatePerAnnum(Customer customer) {
        getAccounts(customer).forEach(account -> {
            account.deposit(account.interestEarned());
        });
    }

    public Transaction getTheLastDepositTransaction(Account account) {
        List<Transaction> transactions = account.transactions;
        if (transactions == null || transactions.isEmpty()) {
            throw new IllegalArgumentException("No transactions.");
        } else {
            return transactions.get(transactions.size() - 1);
        }
    }

    public void addInterestRateDaily(Customer customer) {
        getAccounts(customer).forEach(account -> {
            LocalDate lastTransactionDate = getTheLastDepositTransaction(account).transactionDate;
            while (lastTransactionDate.isBefore(LocalDate.now())) {
                account.deposit(account.interestEarned() / 360);
                lastTransactionDate = lastTransactionDate.plusDays(1L);
            }
        });
    }

    private List<Account> getAccounts(Customer customer) {
        List<Account> accounts = customer.getAccounts();

        if (accounts == null || accounts.isEmpty()) {
            throw new IllegalArgumentException("No accounts.");
        } else {
            return accounts;
        }
    }

}
