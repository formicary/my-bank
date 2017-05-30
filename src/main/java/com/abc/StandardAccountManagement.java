package com.abc;

import com.abc.accounts.CheckingAccount;
import com.abc.accounts.SavingsAccount;
import com.abc.transactions.Deposit;
import com.abc.transactions.Transaction;
import com.abc.transactions.Withdrawal;
import com.google.common.collect.Maps;

import java.util.Map;

import static java.lang.Math.abs;

public class StandardAccountManagement implements AccountManagement {
    private Map<Integer, Account> accounts;

    public StandardAccountManagement() {
        accounts = Maps.newConcurrentMap();
    }

    public void openAccount(Account account) {
        account.setAccountID(accounts.size() + 1);
        accounts.put(accounts.size() + 1, account);

    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts.values())
            total += a.interestEarned();
        return total;
    }

    public String getStatement() {
        StringBuilder statementBuilder = new StringBuilder();
        double total = 0.0;
        for (Account a : accounts.values()) {
            statementBuilder.append(statementForAccount(a) + "\n");
            total += a.getBalance();
        }
        statementBuilder.append("\nTotal In All Accounts " + toDollars(total));
        return statementBuilder.toString();
    }

    public String statementForAccount(Account a) {
        StringBuilder statementBuilder = new StringBuilder();
        if (a instanceof CheckingAccount) {
            statementBuilder.append("Checking Account\n");
        } else if (a instanceof SavingsAccount) {
            statementBuilder.append("Savings Account\n");
        } else {
            statementBuilder.append("Maxi Savings Account\n");
        }

        double total = 0.0;
        double amount;
        for (Transaction t : a.getTransactions()) {

            if (t instanceof Deposit) {
                amount = t.getAmount();
                statementBuilder.append("  deposit" + " " + toDollars(amount) + "\n");
                total += amount;

            } else if (t instanceof Withdrawal) {
                amount = t.getAmount();
                statementBuilder.append("  withdrawal" + " " + toDollars(amount) + "\n");
                total -= amount;

            }
        }
        statementBuilder.append("Total " + toDollars(total));
        return statementBuilder.toString();
    }

    public String toDollars(double d) {
        return String.format("$%,.2f", abs(d));
    }

    public Map<Integer, Account> getAccounts() {
        return accounts;
    }
}
