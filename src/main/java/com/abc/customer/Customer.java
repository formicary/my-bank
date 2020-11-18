package com.abc.customer;

import com.abc.account.TransactionType;
import com.abc.bank.BankUtils;
import com.abc.account.Account;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    @Getter
    private final String name;
    private final List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<>();
    }

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        return accounts.stream()
                .map(Account::interestEarned)
                .reduce(0.0, Double::sum);
    }

    public String getStatement() {
        double total = accounts.stream()
                .map(Account::sumOfTransactions)
                .reduce(0.0, Double::sum);
        return accounts.stream()
                .map((account) -> String.format("\n%s\n", account.statementForAccount()))
                .collect(
                        () -> new StringBuilder(String.format("Statement for %s\n", name)),
                        StringBuilder::append,
                        StringBuilder::append
                )
                .append(String.format("\nTotal In All Accounts %s", BankUtils.formatAmount(total))).toString();
    }

    public void transferAmount(Account from, Account to, double amount) {
        if (!accounts.contains(from) || !accounts.contains(to)) {
            throw new IllegalArgumentException("customer can transfer only between his own accounts");
        }
        from.withdraw(amount);
        to.deposit(amount, TransactionType.CUSTOMER_DEPOSIT);
    }

}
