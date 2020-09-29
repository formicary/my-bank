package com.abc.service;

import com.abc.entity.Account;
import com.abc.entity.Bank;
import com.abc.entity.Customer;
import com.abc.entity.Transaction;

import java.math.BigDecimal;

import static java.lang.Math.abs;

public class ReportGenerator {

    public static String getCustomerStatement(Customer customer) {
        String statement = null;
        statement = "Statement for " + customer.getName() + "\n";
        BigDecimal total = new BigDecimal(0.0);
        for (Account a : customer.getAccounts()) {
            statement += "\n" + statementForAccount(a) + "\n";
            total = total.add(TransactionManager.sumTransactions(a));
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    public String bankCustomerSummary(Bank bank) {
        String summary = "Customer Summary";
        for (Customer c : bank.getCustomers())
            summary += "\n - " + c.getName() + " (" + format(c.getAccounts().size(), "account") + ")";
        return summary;
    }

    private String format(int number, String word) {

        return number + " " + (number == 1 ? word : word + "s");
    }

    private static String statementForAccount(Account a) {
        String s = "";
        s += a.getAccountType().toString() + ": ";

        //Now total up all the transactions
        BigDecimal total = new BigDecimal(0);
        for (Transaction t : a.getTransactions()) {
            s += "  " + (t.amount.intValue() < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total = total.add(t.amount);
        }
        s += "Total " + toDollars(total);
        return s;
    }


    private static String toDollars(BigDecimal d){
        return String.format("$%,.2f", abs(d.doubleValue()));
    }
}
