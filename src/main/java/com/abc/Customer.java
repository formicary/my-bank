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
        return accounts.stream().mapToDouble(Account::interestEarned).sum();
    }

    public String getStatement() {
        StringBuilder statement = new StringBuilder("Statement for " + name + "\n");
        double total = accounts.stream().mapToDouble(Account::sumTransactions).sum();
        for (Account a : accounts) {
            statement.append("\n" + statementForAccount(a) + "\n");
        }
        statement.append("\nTotal In All Accounts " + toDollars(total));
        return statement.toString();
    }

    private String statementForAccount(Account account) {
        String statement =account.getAccountType().getAccountTypeName()+"\n";
        //Now total up all the transactions
        for (Transaction t : account.getTransactions()) {
            statement += "  " + (t.getTransactionType()) + " " + toDollars(t.getTransactionAmount()) + "\n";
        }
        statement += "Total " + toDollars(account.sumTransactions());
        return statement;
    }

    public void transfer(Account fromAccount,Account toAccount, double amount)
    {
        fromAccount.withdraw(amount);
        toAccount.deposit(amount);
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }

    public List<Account> getAccounts()
    {
        return accounts;
    }
}
