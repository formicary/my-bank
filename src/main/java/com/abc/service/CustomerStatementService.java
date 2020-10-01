package com.abc.service;

import com.abc.entity.Account;
import com.abc.entity.Customer;
import com.abc.entity.impl.CustomerImpl;
import com.abc.entity.Transaction;

import java.math.BigDecimal;

import static java.lang.Math.abs;

public class CustomerStatementService {

    private Customer customer;

    public CustomerStatementService(Customer customer){
        this.customer = customer;
    }

    public String customerStatement() {
        StringBuilder statement = new StringBuilder();
        statement.append("Statement for " + customer.getName() + "\n");
        BigDecimal total = new BigDecimal(0.0);
        for (Account a : customer.getAccounts()) {
            statement.append("\n" + accountStatement(a) + "\n");
            total = total.add(TransactionManager.sumTransactions(a));
        }
        statement.append("\nTotal In all Accounts " + toDollars(total));
        return statement.toString();
    }


    private static String accountStatement(Account a) {
        StringBuilder line = new StringBuilder();
        line.append(a.getAccountType().toString() + ": ");

        BigDecimal total = new BigDecimal(0);
        for (Transaction t : a.getTransactions()) {
            line.append("\n  " + (t.amount.intValue() < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount));
            total = total.add(t.amount);
        }
        line.append("\nTotal " + toDollars(total));
        return line.toString();
    }


    private static String toDollars(BigDecimal d){
        return String.format("$%,.2f", abs(d.doubleValue()));
    }


}
