package com.abc;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

class Customer {
    private String name;
    private List<Account> accounts;

    Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<>();
    }

    String getName() {
        return name;
    }

    Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    int getNumberOfAccounts() {
        return accounts.size();
    }

    double getTotalInterestEarned() {
        double total = 0;
        for (Account a : accounts) {
            total += a.calculateInterestEarned();
        }
        return total;
    }

    String getStatement() {
        StringBuilder statement;
        statement = new StringBuilder("Statement for " + name + "\n");
        double total = 0.0;
        for (Account a : accounts) {
            statement.append("\n").append(getStatementForAccount(a)).append("\n");
            total += a.sumTransactions();
        }
        statement.append("\nTotal In All Accounts ").append(toDollars(total));
        return statement.toString();
    }

    private String getStatementForAccount(Account a) {
        StringBuilder s = new StringBuilder();

        //Translate to pretty account type
        switch (a.getAccountType()) {
            case Account.CHECKING:
                s.append("Checking Account\n");
                break;
            case Account.SAVINGS:
                s.append("Savings Account\n");
                break;
            case Account.MAXI_SAVINGS:
                s.append("Maxi Savings Account\n");
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {
            s.append("  ").append(t.AMOUNT < 0 ? "withdrawal" : "deposit").append(" ").append(toDollars(t.AMOUNT)).append("\n");
            total += t.AMOUNT;
        }
        s.append("Total ").append(toDollars(total));
        return s.toString();
    }

    private String toDollars(double d) {
        return String.format("$%,.2f", abs(d));
    }

    void transferFunds(Account fromAccount, Account toAccount, double transferAmount) throws ParseException {
        if (transferAmount <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        }

        // Check if account has enough funds for transfer
        if (fromAccount.sumTransactions() >= transferAmount) {
            fromAccount.withdrawFunds(transferAmount);
            toAccount.depositFunds(transferAmount);
        } else {
            throw new IllegalArgumentException("insufficient funds in account");
        }
    }
}

