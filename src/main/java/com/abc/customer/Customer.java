package com.abc.customer;

import com.abc.helper.Strings;
import com.abc.transaction.Transaction;

import java.util.ArrayList;
import java.util.List;

import static com.abc.helper.Strings.toDollars;

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
        String statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement = statement.concat(String.format("\n %s \n", statementForAccount(a)));
            total += a.sumTransactions();
        }
        return statement + "\nTotal In All Accounts " + toDollars(total);
    }

    private String statementForAccount(Account a) {
        String s = a.getAccountType().toString();

        //Now total up all the transactions
        double total = 0.0;

        for (Transaction t : a.getTransactions()) {
            s = s.concat(t.toString()) + "\n";
            total += t.amount;
        }

        return s + "Total " + toDollars(total);
    }



    @Override
    public String toString() {
        return String.format("%s (%d %s)", name, accounts.size(), Strings.pluralize(accounts.size(), "account"));
    }
}
