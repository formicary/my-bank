package com.abc;

import com.abc.account.Account;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {

    private static final Logger LOG = LogManager.getLogger(Customer.class);
    private final String name;
    private final List<Account> accounts = new ArrayList<>();

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void openAccount(Account account) {
        accounts.add(account);
        LOG.debug("New {} account created for customer {}", account.getType(), getName());
    }

    public void transfer(Account from, Account to, double amount) {
        LOG.debug("Transfer of {} triggered", amount);
        from.withdraw(amount);
        to.deposit(amount);
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.getInterestsEarned();
        return total;
    }

    public String printStatement() {
        StringBuilder statement = new StringBuilder();
        statement.append("Statement for ").append(name).append(System.lineSeparator());
        double total = 0.0;
        for (Account a : accounts) {
            statement.append(System.lineSeparator()).append(getStatementForAccount(a)).append(System.lineSeparator());
            total += a.calculateAccountBalance();
        }
        statement.append(System.lineSeparator()).append("Total In All Accounts ").append(toDollars(total));
        return statement.toString();
    }

    private String getStatementForAccount(Account a) {
        StringBuilder str = new StringBuilder();
        str.append(a.getType()).append(" Account").append(System.lineSeparator());

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.getTransactions()) {
            str.append("  ")
                    .append(t.getTransactionType().getType())
                    .append(" ")
                    .append(toDollars(t.getAmount()))
                    .append(System.lineSeparator());
            total += t.getAmount();
        }
        str.append("Total ").append(toDollars(total));
        return str.toString();
    }

    private String toDollars(double d) {
        return String.format("$%,.2f", abs(d));
    }
}
