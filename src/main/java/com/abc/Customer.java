package com.abc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.log;

public class Customer {
    private static Logger logger = LoggerFactory.getLogger(Customer.class);
    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(Account account) {
        try {
            accounts.add(account);
        } catch (Exception e) {
            logger.error("Error whilst opening account", e);
        }
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    public String getStatement() {
        StringBuilder statement = new StringBuilder("Statement for ").append(name).append("\n");
        double total = 0.0;
        for (Account a : accounts) {
            statement.append("\n").append(statementForAccount(a)).append("\n");
            total += a.sumTransactions();
        }
        statement.append("\nTotal In All Accounts ").append(toDollars(total));
        return statement.toString();
    }

    private String statementForAccount(Account a) {
        StringBuilder s = new StringBuilder();

        //Translate to pretty account type
        switch (a.getAccountType()) {
            case Account.CHECKING -> s.append("Checking Account\n");
            case Account.SAVINGS -> s.append("Savings Account\n");
            case Account.MAXI_SAVINGS -> s.append("Maxi Savings Account\n");
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {
            s.append("  ").append(t.amount < 0 ? "withdrawal" : "deposit").append(" ").append(toDollars(t.amount)).append("\n");
            total += t.amount;
        }
        s.append("Total ").append(toDollars(total));
        return s.toString();
    }

    private String toDollars(double d) {
        return String.format("$%,.2f", abs(d));
    }

    public void transferBetweenAccounts(Account from, Account to, Double amount) {
        if (amount > from.sumTransactions()) {
            throw new IllegalArgumentException("Amount exceeds account balance.");
        } else {
            from.withdraw(amount);
            to.deposit(amount);
            logger.info(String.format("%f has been deposited from %s to %s", amount, from, to));
        }
    }
}
