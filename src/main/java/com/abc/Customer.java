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

    public void transfer(double amount, Account from, Account to) {
        if (accounts.contains(from) && accounts.contains(to)) {
            from.withdraw(amount);
            to.deposit(amount);
        } else {
            throw new IllegalArgumentException("This account does not belong to this customer");
        }
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        
        return total;
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += statementForAccount(a) + "\n\n";
            total += a.sumTransactions();
        }
        statement += "Total in all accounts " + toDollars(total);
        System.out.println(statement);

        return statement;
    }

    private String statementForAccount(Account a) {
        String s = "";

        // Translate to pretty account type
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

        // Account's total

        double totalIn = 0;
        double totalOut = 0;

        for (int i = 1; i < a.transactions.size(); i++) {
            Transaction t = a.transactions.get(i);
            s += "  ";
            if (t.amount < 0) {
                s += "withdrawal ";
                totalOut += t.amount;
            } else {
                s += "deposit ";
                totalIn += t.amount;
            }
            s += toDollars(t.amount) + "\n";
        }
        s += "Total in " + toDollars(totalIn) + "\n";
        s += "Total out " + toDollars(totalOut);
        
        return s;
    }

    private String toDollars(double d) {
        return String.format("$%,.2f", abs(d));
    }

}
