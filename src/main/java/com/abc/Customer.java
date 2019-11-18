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

    public Customer openAccount(Account account) {
        accounts.add(account);
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
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.getBalance();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    /**
     * Generates an account statement, composing of a balance and record for each transaction.
     * @param myAccount the account, for which, to generate a statement
     * @return the account statement
     */
    private String statementForAccount(Account myAccount) {
        StringBuilder statement = new StringBuilder(myAccount.getAccountTypeToString());

        // Appending information about each transaction.
        for (Transaction t : myAccount.getTransactions()) {
            statement.append("\t");
            statement.append((t.amount < 0 ? "withdrawal" : "deposit"));
            statement.append(toDollars(t.amount));
            statement.append("\n");
        }

        statement.append("Total: ");
        statement.append(toDollars(myAccount.getBalance()));

        return statement.toString();
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
