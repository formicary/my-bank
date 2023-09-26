package com.abc.accenture.financial.services;

import com.abc.accenture.financial.items.Transaction;
import com.abc.accenture.financial.items.account.Account;
import com.abc.accenture.financial.items.account.AccountType;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface AccountService {

    Account createAccount(final AccountType accountType);

    default void deposit(final List<Transaction> transactions, final double amount) {
        this.deposit(transactions, amount, LocalDate.now());
    }

    void deposit(final List<Transaction> transactions, final double amount, final LocalDate transactionDate);

    default void withdraw(final List<Transaction> transactions, double amount) {
        this.withdraw(transactions, amount, LocalDate.now());
    }

    void withdraw(final List<Transaction> transactions, double amount, final LocalDate transactionDate);

    double interestEarned(final Account account);

    double sumTransactions(final List<Transaction> transactions);

    boolean isAlreadyAccountName(final String accountName, final Map<String, Account> accounts);

    void transferBetweenAccount(final Account accountFrom, final Account accountTo, final double amount);
}
