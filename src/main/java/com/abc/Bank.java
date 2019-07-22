package com.abc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Bank {
    private List<Customer> customers;

    public Bank() {
        customers = new ArrayList<Customer>();
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
        double total = 0;
        for (Customer c : customers)
            total += c.totalInterestEarned();
        return total;
    }

    public void addFlatRatePerAnnum(Customer customer) {
        for (Account account : getAccounts(customer)) {
            double interestEarned = account.interestEarned();
            account.deposit(interestEarned);
        }
    }

    public Transaction getTheLastDepositTransaction(Account account) {
        List<Transaction> transactions = account.transactions;
        Transaction lastTransaction = transactions.get(transactions.size() - 1);
        return lastTransaction;
    }

    public void addInterestRateDaily(Customer customer) {
        for (Account account : getAccounts(customer)) {
            LocalDate lastTransactionDate = getTheLastDepositTransaction(account).transactionDate;

            while (lastTransactionDate.isBefore(LocalDate.now())) {
                double dailyInterestEarned = account.interestEarned() / 360;
                account.deposit(dailyInterestEarned);
                lastTransactionDate = lastTransactionDate.plusDays(1L);
            }
        }
    }


    private List<Account> getAccounts(Customer customer) {
        List<Account> accounts = customer.getAccounts();

        if (accounts == null || accounts.isEmpty()) {
            throw new IllegalArgumentException("no accounts");
        } else {
            return accounts;
        }
    }

}
