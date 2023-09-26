package com.abc.accenture.financial.services;

import com.abc.accenture.financial.items.Customer;
import com.abc.accenture.financial.items.account.AccountType;

import java.time.LocalDate;

public interface CustomerService {

    CustomerOperation openAccount(final Customer customer, final String accountName, final AccountType accountType);

    void deposit(final Customer customer, final String accountName, double amount);

    void deposit(final Customer customer, final String accountName, double amount, final LocalDate transactionDate);

    void withdraw(final Customer customer, final String accountName, double amount);

    int getNumberOfAccounts(final Customer customer);

    double totalInterestEarned(final Customer customer);

    String getStatement(final Customer customer);

    void transferBetweenAccount(final Customer customer, final String fromAccountName, final String toAccountName,
                                final double amount);
}
