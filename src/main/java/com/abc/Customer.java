package com.abc;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.List;

public class Customer {

    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getCustomerName() {
        return name;
    }

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double getTotalInterestEarned() {
        double total = 0;
        for (Account a : accounts) {
            total += a.interestEarned();
        }
        return total;
    }

    public String getStatement() {
        String statement = "Statement for " + name + "\n";
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
        switch (a.getAccountType()) {
            case 0:
                s += "Checking Account\n";
                break;
            case 1:
                s += "Savings Account\n";
                break;
            case 2:
                s += "Maxi Savings Account\n";
                break;
            default:
                break;
        }
        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {
            //this next line can be improved
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(double d) {
        return String.format("$%,.2f", abs(d));
    }

    public void transferBetweenAccounts(Account from, Account to, double amo) {
        if (from.getBalance() >= amo) {
            from.withdraw(amo);
            to.deposit(amo);
        } else {
            throw new IllegalArgumentException("Selected amount not available, Please try again.");
        }
    }

    public List<Account> getAccounts() {
        return accounts;
    }

}
