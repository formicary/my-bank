package com.abc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(final Account account) {
        //Ensure that a customer cannot open two of the same account
        if (accounts.stream().anyMatch(acc -> acc.getClass() == account.getClass())) {
            return this;
        }
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned(LocalDate finalDate) {
        double total = 0;
        for (Account a : accounts)
            total += a.getInterestEarned(finalDate);
        return total;
    }

    public String getStatement() {
        StringBuilder statement = new StringBuilder("Statement for " + name + "\n");
        double total = 0.0;
        for (Account a : accounts) {
            statement.append("\n")
                    .append(statementForAccount(a))
                    .append("\n");
            total += a.sumTransactions();
        }
        statement.append("\nTotal In All Accounts ").append(toDollars(total));
        return statement.toString();
    }

    //Since there is no policy, a customer can transfer up to any amount
    //even when they don't have it in their account
    public Customer transfer(Account from, Account to, double amount) {
        if (from.getClass() == to.getClass()) return this;

        from.withdraw(amount);
        to.deposit(amount);
        return this;
    }

    private String statementForAccount(Account a) {
        StringBuilder s = new StringBuilder();
        s.append(a.getAccountString());

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {
            s.append("  ")
                    .append(t.getAmount() < 0 ? "withdrawal" : "deposit")
                    .append(" ")
                    .append(toDollars(t.getAmount()))
                    .append("\n");
            total += t.getAmount();
        }
        s.append("Total ").append(toDollars(total));
        return s.toString();
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }


}
