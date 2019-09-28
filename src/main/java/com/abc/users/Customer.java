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

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public void transfer(Account accFrom, Account accTo, double amount){
        accFrom.withdraw(amount);
        accTo.deposit(amount);
    }

    public double totalInterestEarned() { return accounts.stream().mapToDouble(Account::totalInterestEarned).sum(); }

    public String getStatementInDollars() {
        StringBuilder statement = new StringBuilder("Statement for " + getName() + "\n");
        double total = 0.0;
        for (Account a : accounts) {
            statement.append("\n").append(a.getStatementInDollars()).append("\n");
            total += a.getBalance();
        }
        statement.append("\nTotal In All Accounts ").append(toDollars(total));
        return statement.toString();
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}
