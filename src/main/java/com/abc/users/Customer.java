package com.abc.users;

import com.abc.accounts.Account;

import java.util.ArrayList;
import java.util.List;

import static com.abc.util.CurrencyFormatter.toDollars;

public class Customer extends User {

    private List<Account> accounts;

    public Customer(String name) {
        super(name);
        this.accounts = new ArrayList<>();
    }

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    public void transfer(Account accFrom, Account accTo, double amount){
        accFrom.withdraw(amount);
        accTo.deposit(amount);
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        return accounts.stream().mapToDouble(Account::interestEarned).sum();
    }

    public String getStatement() {
        StringBuilder statement = new StringBuilder("Statement for " + getName() + "\n");
        double total = 0.0;
        for (Account a : accounts) {
            statement.append("\n").append(a.getStatement()).append("\n");
            total += a.sumTransactions();
        }
        statement.append("\nTotal In All Accounts ").append(toDollars(total));
        return statement.toString();
    }
}
