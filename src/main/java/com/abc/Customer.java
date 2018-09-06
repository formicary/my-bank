package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {

    private String name;
    private static int count;
    private int id; //customer id to uniquely identify accounts
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        setID(++count);
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
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
        for (Account a : accounts) {
            total += (a.getTotal() - a.sumTransactions());
        }
        return total;
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    private String statementForAccount(Account a) {
        String s = "";

        //Translate to pretty account type
        switch (a.getAccountType()) {
            case Account.CHECKING:
                s += "Checking Account\n";
                break;
            case Account.SAVINGS:
                s += "Savings Account\n";
                break;
            case Account.MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(double d) {
        return String.format("$%,.2f", abs(d));
    }

    //Transfer if both accounts belong to same customer, and the outgoing account has enough money to send
    public void transferBetweenAccounts(Account from, Account to, double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else if (from.getOwnerID() != to.getOwnerID()) {
            throw new IllegalArgumentException("Both accounts must belong to same customer");
        } else if (amount >= from.sumTransactions()) {
            throw new IllegalArgumentException("There are insufficient funds in the account");
        } else {
            from.withdraw(amount);
            to.deposit(amount);
        }
    }
}
