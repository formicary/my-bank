package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

class Customer {
    private String name;
    private List<Account> accounts;

    Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
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
        for (Account a : accounts)
            total += a.calculateInterestEarned();
        return total;
    }

    String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + getStatementForAccount(a) + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    private String getStatementForAccount(Account a) {
        String s = "";

        //Translate to pretty account type
        switch (a.getAccountType()) {
            case Account.CHECKING:
                s += "Checking Account\n";
                break;
            case Account.SAVINGS:
                s += "Savings Account\n";
                break;
            case Account.MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(double d) {
        return String.format("$%,.2f", abs(d));
    }

    void transferFunds(Account fromAccount, Account toAccount, double transferAmount) {
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
        //TODO: Cover negative amount case
    }
}

