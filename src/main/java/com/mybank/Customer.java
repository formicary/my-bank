package com.mybank;

import com.mybank.Accounts.Account;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private static final Logger logger = LoggerFactory.getLogger(Customer.class);
    private final String name;
    private final List<Account> accounts;

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
        double total = 0.0;
        for (Account account : accounts)
            total += account.interestEarned();
        return total;
    }

    public String getStatement() {
        StringBuilder statement = new StringBuilder("Statement for ").append(name).append("\n");
        double total = 0.0;
        for (Account account : accounts) {
            statement.append("\n")
                    .append(statementForAccount(account))
                    .append("\n");
            total += account.sumTransactions();
        }
        statement.append("\nTotal In All Accounts ").append(toDollars(total));
        return statement.toString();
    }

    private String statementForAccount(Account account) {
        StringBuilder stringBuilder = new StringBuilder();

        switch (account.getAccountType()) {
            case CHECKING -> stringBuilder.append("Checking Account\n");
            case SAVINGS -> stringBuilder.append("Savings Account\n");
            case MAXI_SAVINGS -> stringBuilder.append("Maxi Savings Account\n");
        }

        double total = 0.0;
        for (Transaction transaction : account.getTransactions()) {
            stringBuilder.append("  ")
                    .append(transaction.amount < 0 ? "withdrawal" : "deposit")
                    .append(" ")
                    .append(toDollars(transaction.amount)).append("\n");
            total += transaction.amount;
        }
        stringBuilder.append("Total ")
                .append(toDollars(total));
        return stringBuilder.toString();
    }

    private String toDollars(double total) {
        return String.format("$%,.2f", abs(total));
    }

    public void transferBetweenAccounts(Account from, Account to, double amount) {
        if (amount > from.sumTransactions()) {
            throw new IllegalArgumentException("Amount exceeds account balance.");
        } else {
            from.withdraw(amount);
            to.deposit(amount);
            logger.info(String.format("%f has been deposited from %s to %s", amount, from, to));
        }
    }
}
