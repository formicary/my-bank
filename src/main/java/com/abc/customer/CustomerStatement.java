package com.abc.customer;

import com.abc.account.Transaction;
import com.abc.account.Account;

import static java.lang.Math.abs;

public final class CustomerStatement {

    private CustomerStatement() {
    }

    private static final String LINE_BREAK = "\n";

    public static String create(Customer customer) {
        StringBuilder statement = new StringBuilder("Statement for ");
        statement.append(customer.getName());
        statement.append(LINE_BREAK);
        for (Account account : customer.getAccounts()) {
            statement.append(LINE_BREAK);
            statement.append(statementForAccount(account));
            statement.append(LINE_BREAK);
        }
        statement.append(LINE_BREAK);
        statement.append("Total In All Accounts ");
        statement.append(toDollars(sumAllTransactions(customer)));
        return statement.toString();
    }

    private static String statementForAccount(Account account) {
        StringBuilder sb = new StringBuilder(account.getAccountType().getPrettyName());
        sb.append(LINE_BREAK);
        for (Transaction t : account.getTransactions()) {
            if (t.getAmount() < 0) {
                sb.append("  withdrawal ");
            } else {
                sb.append("  deposit ");
            }
            sb.append(toDollars(t.getAmount()));
            sb.append(LINE_BREAK);
        }
        sb.append("Total ");
        sb.append(toDollars(account.sumTransactions()));
        return sb.toString();
    }

    private static double sumAllTransactions(Customer customer) {
        double total = 0.0;
        for (Account account : customer.getAccounts()) {
            total += account.sumTransactions();
        }
        return total;
    }

    private static String toDollars(double d) {
        return String.format("$%,.2f", abs(d));
    }
}
