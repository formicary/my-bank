package com.abc;

import java.util.ArrayList;
import java.util.List;

public class Customer extends Utils {

    private final String name;
    private final List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<>();
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
            total += a.getInterestEarned();
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

        //Translate to pretty account type
        if (a instanceof CheckingAccount) {
            s = s.concat("Checking Account\n");
        } else if (a instanceof SavingsAccount) {
            s = s.concat("Savings Account\n");
        } else if (a instanceof MaxiSavingsAccount) {
            s = s.concat("Maxi Savings Account\n");
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

    public void transfer(Account from, Account to, double d) {
        from.withdraw(d);
        to.deposit(d);
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}
