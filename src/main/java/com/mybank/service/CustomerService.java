package com.mybank.service;

import com.mybank.entity.Account;
import com.mybank.entity.Customer;
import com.mybank.util.CurrencyConverter;

public class CustomerService {

    private AccountService accountService = new AccountService();

    /**
     * Add text
     *
     * @param customer
     * @param account
     * @return
     */
    public Customer openAccount(Customer customer, Account account) {
        customer.getAccounts().add(account);
        return customer;
    }

    /**
     * Add text
     *
     * @param customer
     * @return
     */
    public double totalInterestEarned(Customer customer) {
        double total = 0.0;
        for (Account a : customer.getAccounts()) {
            total += accountService.interestEarned(a);
        }
        return total;
    }

    /**
     * Add text
     *
     * @param customer
     * @return
     */
    public String getStatement(Customer customer) {
        String statement = "Statement for " + customer.getName() + "\n";
        double total = 0.0;
        for (Account a : customer.getAccounts()) {
            statement += "\n" + accountService.statementForAccount(a) + "\n";
            total += accountService.sumTransactions(a);
        }
        statement += "\nTotal In All Accounts " + CurrencyConverter.toDollars(total);
        return statement;
    }
}
