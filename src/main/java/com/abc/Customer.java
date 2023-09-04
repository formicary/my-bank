package com.abc;

import com.util.BigDecimalProvider;
import com.util.CurrencyStringFormatter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    private final String name;
    private final List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void openAccount(Account account) {
        accounts.add(account);
    }

    public void transfer(Account origin, Account target, BigDecimal amount) throws IllegalStateException {
        if (!(checkAccounts(origin, target))) {
            throw new IllegalArgumentException("One or more of provided accounts do not belong to the customer!");
        }

        Transaction withdrawal = origin.withdraw(amount);
        origin.verifyTransaction(withdrawal);

        try {
            Transaction deposit = target.deposit(amount);
            target.verifyTransaction(deposit);
        } catch (IllegalStateException exception) {
            origin.rollbackTransaction(withdrawal);
            throw exception;
        }
    }

    private boolean checkAccounts(Account origin, Account target) {
        return accounts.contains(origin) && accounts.contains(target);

    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public BigDecimal totalInterestEarned() {
        BigDecimal total = BigDecimalProvider.getZero();
        for (Account account : accounts) {
            total = total.add(account.getInterestEarned());
        }
        return total;
    }

    public String getStatement() {
        StringBuilder statement = new StringBuilder("Statement for " + name + "\n");
        BigDecimal total = BigDecimalProvider.getZero();

        for (Account account : accounts) {
            statement.append("\n");
            statement.append(account.getStatement());
            statement.append("\n");
            total = total.add(account.sumTransactions());
        }
        statement.append("\nTotal In All Accounts ");
        statement.append(CurrencyStringFormatter.format(total));

        return statement.toString();
    }

}
