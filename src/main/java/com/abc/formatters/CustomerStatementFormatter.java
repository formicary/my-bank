package com.abc.formatters;

import com.abc.domain.Account;
import com.abc.domain.Customer;

import java.util.List;

/**
 * Provides a statement formatter for Customers.
 */
public class CustomerStatementFormatter {

    private final Customer customer;

    /**
     * Constructor.
     *
     * @param customer The Customer object. The subject of the statement.
     */
    public CustomerStatementFormatter(Customer customer) {
        this.customer = customer;
    }

    /**
     * @return The formatted statement.
     */
    public String getStatement() {
        final List<Account> accounts = customer.getAccounts();

        StringBuilder builder = new StringBuilder();
        appendHeader(builder);
        appendAccounts(builder, accounts);
        appendTotal(builder, accounts);
        return builder.toString();
    }

    private void appendHeader(StringBuilder statement) {
        statement.append("Statement for ");
        statement.append(customer.getName());
        statement.append("\n");
    }

    private void appendAccounts(StringBuilder builder, List<Account> accounts) {
        for (final Account a : accounts) {
            builder.append("\n");
            builder.append(statementForAccount(a));
            builder.append("\n");
        }
    }

    private String statementForAccount(Account a) {
        return new AccountStatementFormatter(a).getStatement();
    }

    private void appendTotal(StringBuilder builder, final List<Account> accounts) {
        final double total = getTotal(accounts);
        builder.append("\nTotal In All Accounts ");
        builder.append(CurrencyFormatter.INSTANCE.toDollars(total));
    }

    private double getTotal(List<Account> accounts) {
        double total = 0.0;
        for (final Account a : accounts) {
            total += a.getBalance();
        }
        return total;
    }

}
