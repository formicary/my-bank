package com.abc;

import java.util.ArrayList;
import java.util.List;
import com.abc.account.*;

public class Customer {
    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public void openAccount(Account account) {
        accounts.add(account);
    }

    public void transferMoney(Account source, Account destination, double amount) {
        try {
            source.withdraw(amount);
        } catch (IllegalArgumentException e) {
              e.printStackTrace();
              return;
        }
        destination.deposit(amount);
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    public String getStatement() {
        String stmt = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            stmt += "\n" + a.accountStatement() + "\n";
            total += a.balance();
        }
        stmt += "\nTotal In All Accounts " + Transaction.toDollars(total);
        return stmt;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public String getName() {
        return name;
    }
}
