package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.Math.BigDecimal;

import static java.lang.Math.abs;

public class Customer {
    
    private String name;
    private List<Account> accounts;

    protected Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    protected String getName() {
        return name;
    }

    protected Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    protected int getNumberOfAccounts() {
        return accounts.size();
    }

    protected double totalInterestEarned() {
        double total = 0.00;
        for (Account a : accounts) {
            total += a.interestEarned();
        }
        return total;
    }
     
    private String toDollars(double d) {
        return String.format("%1$.2f", abs(d));
    }
    
    protected String getStatement() {
        String statement;
        double total = 0.00;
        statement = String.format("Statement for %1$s \n", name);
        for (Account a : accounts) {
            statement += String.format("\n %1$s \n", statementForAccount(a));
            total += a.sumTransactions();
        }
        statement += String.format("\nTotal In All Accounts %1$s", toDollars(total));
        return statement;
    }

    private String statementForAccount(Account a) {
        double total = 0.00;
        String s;
        switch (a.getAccountType()) {
            case Account.CHECKING:
                s = "Checking Account\n";
                break;
            case Account.SAVINGS:
                s = "Savings Account\n";
                break;
            case Account.MAXI_SAVINGS:
                s = "Maxi Savings Account\n";
                break;
            default:
                throw new IllegalArgumentException("Invalid account type");
        }
        for (Transaction t : a.transactions) {
            if (t.amount < 0) {
                s += String.format("  withdrawal %1$s\n", toDollars(t.amount));
            } else {
                s += String.format("  deposit %1$s\n", toDollars(t.amount));
            }
            total += t.amount;
        }
        s += String.format("Total %1$s", toDollars(total));
        return s;
    }
}
