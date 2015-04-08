package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

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

    // Adjusted to take variable number of accounts, that way openAccount does not need to be called repeatedly.
    public Customer openAccount(Account... accounts_in) {
        for(Account a:accounts_in) {
            accounts.add(a);
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

    // Implemented here to maintain encapsulation within a Customer's accounts.
    public void transfer(double amount, Account from, Account to){
        if (amount <= 0){
            throw new IllegalArgumentException("Amount must be greater than zero.");
        } else {
            from.transactions.add(new Transaction(-amount));
            to.transactions.add(new Transaction(amount));
        }
    }

    /* Individual account statement function assimilated into getStatement to avoid having to parse the list of
        transactions multiple times; here a single pass is done which should help efficiency.
     */
    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            double runningtotal = 0;
            statement += "\n" + a.getAccountType().toString() + "\n";
            for (Transaction t : a.transactions) {
                statement += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
                runningtotal += t.amount;
            }
            statement += "Total " + toDollars(runningtotal) + "\n";
            total += runningtotal;
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
