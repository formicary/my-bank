package com.abc;

import lombok.Getter;
import lombok.NonNull;

import java.util.*;

import static com.abc.Bank.*;
import static com.abc.Account.AccountType;

@Getter
public class Customer {

    @NonNull
    private String name;
    @NonNull
    private Map<AccountType, Account> accounts;

    public Customer(@NonNull String name) {
        this.name = name;
        this.accounts = new TreeMap<AccountType, Account>();
    }

    public Account openAccount(@NonNull AccountType accountType) {
        Account account;
        if (accounts.containsKey(accountType))
            return account = accounts.get(accountType); //already opened
        accounts.put(accountType, account = new Account(accountType));
        return account;
    }

    public boolean transferBetweenAccounts(@NonNull AccountType from, @NonNull AccountType to, @NonNull Double amount) throws Exception {
        Account fromAccount = accounts.get(from);
        Account toAccount = accounts.get(to);
        if (fromAccount == null || toAccount == null)
            throw new Exception("No " + from + " or " + to + " account!");

        if (fromAccount.withdraw(amount)) {
            toAccount.deposit(amount);
            return true;
        }
        return false;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public Double getTotalInterestEarned() {
        return accounts.values().stream().mapToDouble(Account::getInterestEarned).sum();
    }

    public String printStatement() {
        StringBuilder statement = new StringBuilder("Statement for " + name + "\n");
        Double total = 0d;
        for (Account a : accounts.values()) {
            statement.append("\n" + a.printStatement() + "\n");
            total += a.getBalance();
        }
        statement.append("\nTotal in all Accounts " + DOLLAR.format(total));
        return statement.toString();
    }

}
