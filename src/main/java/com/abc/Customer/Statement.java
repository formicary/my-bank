package com.abc.Customer;

import com.abc.Account.Account;
import com.abc.Customer.Customer;
import com.abc.Transaction;

import java.util.ArrayList;

import static java.lang.Math.abs;

public class Statement {
    private Customer customer;

    public Statement(Customer customer){
        this.customer = customer;
    }

    public String getStatement() {
        ArrayList<Account> accounts = customer.getAccounts();
        String statement = null;
        statement = "Statement for " + customer.getName() + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.calculateBalance();
        }
        statement += "\nTotal In All Account " + toDollars(total);
        return statement;
    }

    private String statementForAccount(Account a) {
        String s = "";
        s = s + a.getName() + "\n";
        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.getTransactions()) {
            s += "  " + (t.getAmount() < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.getAmount()) + "\n";
            total += t.getAmount();
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }


}
