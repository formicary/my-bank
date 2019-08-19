package com.abc;

import java.util.ArrayList;
import java.util.List;

import static com.abc.Utility.toDollars;

public class Customer {
    private String name;
    protected List<Account> accounts = new ArrayList<Account>();

    public Customer(String name) {
        this.name = name;
    }

    public void openAccount(Account account) {
        accounts.add(account);
    }

    public void transferBetweenAccounts(Account withdrawAccount, Account depositAccount, Double amount) {
        try {
            withdrawAccount.withdraw(amount);
            depositAccount.deposit(amount);
        } catch (Exception e) {
            throw new IllegalArgumentException("amount must be greater than zero and less than or equal to the widthrawing account's value");
        }
    }

    public double totalInterestEarned() {
        double total = 0;

        for (Account account : accounts)
            total += account.interestEarned();

        return total;
    }

    public String getStatement() {
        StringBuilder statement = new StringBuilder("Statement for " + name + "\n");
        double total = 0;

        for (Account account : accounts) {
            statement.append("\n").append(account.getAccountStatement()).append("\n");
            total += account.getAccountValue();
        }

        statement.append("\nTotal In All Accounts ").append(toDollars(total));

        return statement.toString();
    }

    public String getName() {
        return name;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }
}
