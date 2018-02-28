package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    public String getStatement() {
        StringBuilder sb = new StringBuilder("Statement for " + name + "\n");
        double total = 0.0;
        for (Account a : accounts) {
            sb.append("\n");
            sb.append(statementForAccount(a));
            sb.append("\n");
            total += a.sumTransactions();
        }
        sb.append("\nTotal In All Accounts ");
        sb.append(FormatUtils.toDollars(total));
        return sb.toString();
    }

    private String statementForAccount(Account a) {
        StringBuilder sb = new StringBuilder();

        //Translate to pretty account type
        switch (a.getAccountType()) {
            case CHECKING:
                sb.append("Checking Account\n");
                break;
            case SAVINGS:
                sb.append("Savings Account\n");
                break;
            case MAXI_SAVINGS:
                sb.append("Maxi Savings Account\n");
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.getTransactions()) {
            sb.append(
                    String.format("  %s %s\n", t.amount < 0 ? "withdrawal" : "deposit", FormatUtils.toDollars(Math.abs(t.amount)))
            );
            total += t.amount;
        }
        sb.append("Total ");
        sb.append(FormatUtils.toDollars(total));
        return sb.toString();
    }
}
