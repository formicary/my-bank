package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

public class Customer {
    private final String name;
    private final List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }

    public void openAccount(Account account) {
        accounts.add(account);
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public BigDecimal totalInterestEarned() {
        BigDecimal total = BigDecimal.ZERO;
        for (Account a : accounts)
            total = total.add(a.interestEarned());
        return total;
    }

    public String getStatement() {
        StringBuilder statement = new StringBuilder();
        
        statement.append("Statement for ");
        statement.append(name);
        statement.append("\n");
        
        BigDecimal total = BigDecimal.ZERO;
        for (Account a : accounts) {
            statement.append("\n");
            statement.append(a.statementForAccount());
            statement.append("\n");
            total = total.add(a.sumTransactions());
        }
        statement.append("\nTotal In All Accounts ");
        statement.append(Account.toDollars(total));
        return statement.toString();
    }
}
