package com.abc.Customer;

import com.abc.Account.Account;
import com.abc.Money;
import com.abc.Transaction;

;
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
        Money total = new Money("0");
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total = total.add(a.getBalance());
        }
        statement += "\nTotal In All Account " + total.toString();
        return statement;
    }

    private String statementForAccount(Account a) {
        String s = "";
        s = s + a.getName() + "\n";
        //Now total up all the transactions
        Money total = new Money("0");
        for (Transaction t : a.getTransactions()) {
            s += "  " + t.getType() + " " + t.getAmount().toString() + "\n";
            total = total.add(t.getAmount());
        }
        s += "Total " + total.toString();
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }


}
