package com.abc.accenture.financial.services;

import com.abc.accenture.financial.items.Transaction;
import com.abc.accenture.financial.items.account.Account;
import com.abc.accenture.financial.items.account.AccountGenerator;
import com.abc.accenture.financial.items.account.AccountType;
import com.abc.accenture.financial.items.exceptions.AccountServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service(value = "accountService")
public class AccountServiceImpl implements AccountService {

    private final AccountGenerator accountGenerator;

    @Autowired
    public AccountServiceImpl(final AccountGenerator accountGenerator) {
        this.accountGenerator = accountGenerator;
    }

    @Override
    public Account createAccount(final AccountType accountType) {
        return accountGenerator.createAccount(accountType);
    }

    @Override
    public void deposit(List<Transaction> transactions, double amount, LocalDate transactionDate) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount, transactionDate));
        }
    }

    @Override
    public void withdraw(List<Transaction> transactions, double amount, LocalDate transactionDate) {
        if (amount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(-amount, transactionDate));
        }
    }

    @Override
    public double interestEarned(final Account account) {
        return account.interestEarned(sumTransactions(account.getTransactions()));
    }

    @Override
    public double sumTransactions(final List<Transaction> transactions) {
        return checkIfTransactionsExist(transactions);
    }

    @Override
    public boolean isAlreadyAccountName(final String accountName, final Map<String, Account> accounts) {
        return accounts.containsKey(accountName);
    }

    @Override
    public void transferBetweenAccount(final Account accountFrom, final Account accountTo, double amount) {
        if (this.sumTransactions(accountFrom.getTransactions()) - amount >= 0) {
            this.withdraw(accountFrom.getTransactions(), amount);
            this.deposit(accountTo.getTransactions(), amount);
        } else {
            throw new AccountServiceException("This account doesn't have enough coverage. ");
        }
    }

    private double checkIfTransactionsExist(final List<Transaction> transactions) {
        return transactions.stream().mapToDouble(Transaction::amount).sum();
    }
}
