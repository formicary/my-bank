package com.abc.accenture.financial.services;

import com.abc.accenture.financial.items.Bank;
import com.abc.accenture.financial.items.Customer;
import com.abc.accenture.financial.items.account.AccountType;

import java.time.LocalDate;

public interface BankService {

    default CustomerOperation openAccount(final Bank bank, final String accountName, final String name) {
        return this.openAccount(bank, name, accountName, AccountType.CHECKING);
    }

    default void deposit(final Bank bank, final String customerName, final String accountName, double amount) {
        this.deposit(bank, customerName, accountName, amount, LocalDate.now());
    }

    void deposit(final Bank bank, final String customerName, final String accountName, double amount, final LocalDate transactionDate);

    default void withdraw(final Bank bank, final String customerName, final String accountName, double amount) {
        this.withdraw(bank, customerName, accountName, amount, LocalDate.now());
    }

    void withdraw(final Bank bank, final String customerName, final String accountName, double amount, final LocalDate transactionDate);

    CustomerOperation openAccount(final Bank bank, final String name, final String accountName, final AccountType accountType);

    void addCustomer(final Bank bank, final Customer customer);

    Customer getCustomer(final Bank bank, final String customerName);

    String customerSummary(final Bank bank);

    double totalInterestPaid(final Bank bank);
}
