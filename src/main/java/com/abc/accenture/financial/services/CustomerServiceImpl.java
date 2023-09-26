package com.abc.accenture.financial.services;

import com.abc.accenture.financial.items.Customer;
import com.abc.accenture.financial.items.account.Account;
import com.abc.accenture.financial.items.account.AccountType;
import com.abc.accenture.financial.items.exceptions.CustomerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static java.lang.Math.abs;

@Service(value = "customerService")
public class CustomerServiceImpl implements CustomerService {

    private final AccountService accountService;

    @Autowired
    public CustomerServiceImpl(AccountService accountService) {
        this.accountService = accountService;
    }

    @Override
    public CustomerOperation openAccount(final Customer customer, final String accountName,
                                         final AccountType accountType) {
        if (!accountService.isAlreadyAccountName(accountName, customer.getAccounts())) {
            customer.getAccounts().put(accountName, accountService.createAccount(accountType));
        } else {
            throw new CustomerException("Account can't create because this account name is already : " + accountName);
        }
        customer.getAccounts().put(accountName, accountService.createAccount(accountType));
        return new CustomerOperation(customer, accountService);
    }

    @Override
    public void deposit(final Customer customer, final String accountName, double amount) {
        accountService.deposit(customer.getAccounts().get(accountName).getTransactions(), amount);
    }

    @Override
    public void deposit(Customer customer, String accountName, double amount, LocalDate transactionDate) {
        accountService.deposit(customer.getAccounts().get(accountName).getTransactions(), amount, transactionDate);
    }

    @Override
    public void withdraw(Customer customer, String accountName, double amount) {
        accountService.withdraw(customer.getAccounts().get(accountName).getTransactions(), amount);
    }

    @Override
    public int getNumberOfAccounts(final Customer customer) {
        return customer.getAccounts().size();
    }

    @Override
    public double totalInterestEarned(final Customer customer) {
        return customer.getAccounts().values()
                .stream()
                .mapToDouble(accountService::interestEarned)
                .sum();
    }

    @Override
    public String getStatement(final Customer customer) {
        StringBuilder statement = new StringBuilder("Statement for " + customer.getName() + "\n");
        customer.getAccounts()
                .values()
                .forEach(account -> statement.append("\n").append(statementForAccount(account)).append("\n"));

        statement.append("\nTotal In All Accounts ").append(toDollars(sumAccountsOfTransactions(
                customer.getAccounts().values().stream().toList())));

        return statement.toString();
    }

    @Override
    public void transferBetweenAccount(final Customer customer, final String fromAccountName,
                                       final String toAccountName, final double amount) {
        Account accountFrom = customer.getAccounts().get(fromAccountName);
        Account accountTo = customer.getAccounts().get(toAccountName);

        accountService.transferBetweenAccount(accountFrom, accountTo, amount);
    }

    private double sumAccountsOfTransactions(final List<Account> accounts) {
        return accounts.stream()
                .map(Account::getTransactions)
                .mapToDouble(accountService::sumTransactions)
                .sum();
    }

    private String statementForAccount(final Account account) {
        StringBuilder statement = new StringBuilder(account.getAccountType().getNameOfTittle()).append("\n");
        account.getTransactions()
                .forEach(transaction -> statement.append("  ").append(
                                transaction.amount() < 0 ? "withdrawal" : "deposit").append(" ")
                        .append(toDollars(transaction.amount())).append("\n"));

        statement.append("Total ").append(toDollars(accountService.sumTransactions(account.getTransactions())));
        return statement.toString();
    }

    private String toDollars(double d) {
        return String.format("$%,.2f", abs(d));
    }
}
