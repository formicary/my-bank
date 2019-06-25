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

    public Account openCheckingAccount(){
        MaxiSavingAccount acc = new MaxiSavingAccount();
        accounts.add(acc);
        return acc;
    }

    public Account openMaxiSavingsAccount(){
        MaxiSavingsAccount acc = new MaxiSavingsAccount();
        accounts.add(acc);
        return acc;
    }

    public Account openSavingsAccount(){
        SavingsAccount acc = new SavingsAccount();
        accounts.add(acc);
        return acc;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;

        for (Account a : accounts) total += a.interestEarned();
        return total;
    }

    public String getStatement() {
        StringBuilder statement = new StringBuilder("Statement for " + this.name + "\n");
        double total = 0.0;
        for (Account a : accounts) {
            statement.append("\n").append(statementForAccount(a)).append("\n");
            total += a.getAccountBalance();
        }
        statement.append("\nTotal In All Accounts ").append(toDollars(total));
        return statement.toString();
    }

    private String statementForAccount(Account a) {
        String s = a.getAccountTypeString() + " Account\n";

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
