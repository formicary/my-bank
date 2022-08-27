package com.abc.customer;

import com.abc.bank.Bank;
import com.abc.account.Account;
import com.abc.account.AccountType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Customer {
    private final String name;
    private final List<Account> accounts;
    private Bank bank;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<>();
    }

    public Account openAccount(AccountType accountType) {
        if (bank == null) {
            throw new NullPointerException("Customer should be assigned to a bank first.");
        }
        Account account = bank.createAccount(this, accountType);
        accounts.add(account);
        return account;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0.0;
        for (Account a : accounts)
            total += a.calcInterestEarned();
        return total;
    }

    public String getStatement() {
        return CustomerStatement.create(this);
    }

    public String getName() {
        return name;
    }

    public List<Account> getAccounts() {
        return Collections.unmodifiableList(accounts);
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }
}
