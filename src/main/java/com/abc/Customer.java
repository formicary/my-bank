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

    public void transfer(int origin, int destination, double amount) {
        if (accounts.size() < 2) {
            throw new IllegalArgumentException("Must have at least 2 accounts to transfer money");
        }
        if (origin < 0 || origin >= accounts.size()) {
            throw new IllegalArgumentException(
                String.format("Index of origin account must be in range [%d, %d)", 0, accounts.size())
            );
        }
        if (destination < 0 || destination >= accounts.size()) {
            throw new IllegalArgumentException(
                String.format("Index of destination account must be in range [%d, %d)", 0, accounts.size())
            );
        }
        if (origin == destination)  {
            throw new IllegalArgumentException("Origin and destination accounts must not be the same");
        }
        Account from = accounts.get(origin);
        // If there was a negative limit, check should be made to check whether account has enough funds
        Account to = accounts.get(destination);
        from.withdraw(amount);
        to.deposit(amount);
    }
}
