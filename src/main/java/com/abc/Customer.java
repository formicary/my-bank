package com.abc;

import com.abc.accounts.Account;

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
        for (Account account : accounts){
            total += account.totalInterests();
        }
        return total;
    }

    public String getStatement() {
        StringBuilder statement = new StringBuilder();
        double total = 0.0;

        statement.append("Statement for ").append(name).append("\n");

        for (Account account : accounts) {
            statement.append("\n").append(statementForAccount(account)).append("\n");

            total += account.sumTransactions();
        }
        statement.append("\n").append("Total In All Accounts ").append(toDollars(total));

        return statement.toString();
    }

    private String statementForAccount(Account account) {
        StringBuilder text = new StringBuilder();
        text.append(account.getType());

        //Now total up all the transactions
        double totalAmount = 0.0;
        for (Transaction transaction : account.transactions) {
            text.append("  ").append(transaction.amount < 0 ? "withdrawal" : "deposit");
            text.append(" ").append(toDollars(transaction.amount)).append("\n");

            totalAmount += transaction.amount;
        }

        text.append("Total ").append(toDollars(totalAmount));

        return text.toString();
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }

    public void transferMoneyTo(Account from, Account to, double amount) throws IllegalArgumentException{

        //customer can transfer only through his accounts
        if(this.accounts.contains(from) && this.accounts.contains(to)){

            from.withdraw(amount);
            to.deposit(amount);

            //updating transaction lists for both accounts not necessary --> withdraw() and deposit() did it
        }
        else {
            throw new IllegalArgumentException("account does not belong to selected customer");
        }
    }
}
