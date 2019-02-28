package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    public int numberOfAccounts() {
        return accounts.size();
    }

    public Account getAccount(int accountId) {
        try {
            return this.accounts.get(accountId);
        } catch (IndexOutOfBoundsException e) {
            throw new IllegalArgumentException("account " + accountId + " does not exist");
        }
    }

    public void transfer(int fromAccountId, int toAccountId, double amount) {
        this.getAccount(fromAccountId).withdraw(amount);
        this.getAccount(toAccountId).deposit(amount);
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account acc : accounts) {
            total += acc.interestEarned();
        }
        return total;
    }

    public String getStatement() {
        String statement = "Statement for " + this.name + "\n\n";

        double total = 0.0;
        for (Account acc : accounts) {
            statement += acc.getStatement() + "\n";
            total += acc.currentBalance();
        }

        statement += "Total In All Accounts " + Transaction.toDollars(total);
        return statement;
    }

    private static String formatPlural(int number, String word) {
        return number + " " + (number == 1 ? word : word + "s");
    }

    public String getSummary() {
        return this.name + " (" + formatPlural(this.numberOfAccounts(), "account") + ")";
    }

}
