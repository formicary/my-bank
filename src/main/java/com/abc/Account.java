package com.abc;

import com.util.BigDecimalProvider;
import com.util.CurrencyStringFormatter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


public abstract class Account {
    protected List<Transaction> transactions = new ArrayList<>();

    abstract protected String getAccountTypeLabel();

    abstract public BigDecimal getInterestEarned();


    protected Transaction deposit(BigDecimal amount) {
        Account.validateAmount(amount);
        return commitTransaction(BigDecimalProvider.format(amount));
    }

    protected Transaction withdraw(BigDecimal amount) {
        Account.validateAmount(amount);
        return commitTransaction(BigDecimalProvider.format(amount).negate());
    }

    protected static void validateAmount(BigDecimal amount) {
        if (amount.compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Provided amount must be greater than zero");
        }
    }

    protected Transaction commitTransaction(BigDecimal amount) {
        Transaction transaction = new Transaction(amount);
        transactions.add(transaction);
        return transaction;
    }

    BigDecimal calculateInterest(BigDecimal amount, BigDecimal rate) {
        return amount.multiply(rate).setScale(2, RoundingMode.HALF_EVEN);
    }

    public BigDecimal sumTransactions() {
        BigDecimal amount = BigDecimalProvider.getZero();

        if (transactions.isEmpty()) {
            return amount;
        }

        for (Transaction transaction : transactions)
            amount = amount.add(transaction.getAmount());
        return amount;
    }

    public List<Transaction> getTransactions() {
        return new ArrayList<>(transactions);
    }

    public String getStatement() {
        StringBuilder statement = new StringBuilder();
        String accountTypeLabel = getAccountTypeLabel();
        statement.append(accountTypeLabel);
        statement.append("\n");

        BigDecimal total = BigDecimalProvider.getZero();
        for (Transaction transaction : this.getTransactions()) {
            statement.append(" ");
            statement.append(transaction.getAmount().compareTo(BigDecimal.ZERO) < 0 ? "withdrawal" : "deposit");
            statement.append(" ");
            statement.append(CurrencyStringFormatter.format(transaction.getAmount()));
            statement.append("\n");
            total = total.add(transaction.getAmount());
        }
        statement.append("Total ");
        statement.append(CurrencyStringFormatter.format(total));

        return statement.toString();
    }

    protected void verifyTransaction(Transaction transaction) {
        if (!(transactions.contains(transaction))) {
            throw new IllegalStateException("This account does not contain given transaction!");
        }

    }

    protected void rollbackTransaction(Transaction transaction) {
        verifyTransaction(transaction);
        transactions.remove(transaction);
    }


}
