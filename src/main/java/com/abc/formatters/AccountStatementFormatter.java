package com.abc.formatters;

import com.abc.domain.Account;
import com.abc.domain.Transaction;

import java.util.List;

import static java.lang.Math.abs;

/**
 * Provides a statement formatter for Accounts.
 */
public class AccountStatementFormatter {

    private final Account account;

    /**
     * Constructor.
     *
     * @param account The Account object. The subject of the statement.
     */
    public AccountStatementFormatter(final Account account) {
        this.account = account;
    }

    /**
     * @return The formatted statement.
     */
    public String getStatement() {
        final StringBuilder sb = new StringBuilder();
        appendAccountType(sb, account);
        appendTransactions(sb, account.getTransactions());
        appendTotal(sb, account);
        return sb.toString();
    }

    private void appendAccountType(StringBuilder sb, Account a) {
        sb.append(a.getAccountType().getName());
        sb.append("\n");
    }

    private void appendTransactions(StringBuilder sb, List<Transaction> transactions) {
        for (final Transaction t : transactions) {
            final double amount = t.getAmount();
            sb.append("  ");
            sb.append(transactionStatementEntry(amount));
            sb.append("\n");
        }
    }

    private String transactionStatementEntry(double amount) {
        if (amount < 0) {
            return withdrawalStatementEntry(amount);
        } else {
            return depositStatementEntry(amount);
        }
    }

    private String withdrawalStatementEntry(double amount) {
        return "withdrawal " + toDollars(amount);
    }

    private String depositStatementEntry(double amount) {
        return "deposit " + toDollars(amount);
    }

    private void appendTotal(StringBuilder sb, Account account) {
        double total = account.getBalance();
        sb.append("Total ");
        sb.append(toDollars(total));
    }

    private String toDollars(final double d) {
        return String.format("$%,.2f", abs(d));
    }

}
