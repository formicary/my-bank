package com.accenture;

import com.accenture.accounts.Account;

import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }



    public String getStatement(List<Account> accounts) {
        String statement;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + a.statementForAccount() + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }


    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
