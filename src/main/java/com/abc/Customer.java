package com.abc;

import java.util.ArrayList;
import java.util.List;

import static com.abc.AccountType.*;
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

    public void deposit(Account a, double value) {
        if (accounts.contains(a)) {
            a.deposit(value);
        }
    }

    public void withdraw(Account a, double value) {
        if (accounts.contains(a)) {
            a.withdraw(value);
        }
    }

    public void transfer(Account a, Account b, double value) {
        ArrayList<Account> transferAccounts = new ArrayList<Account>();
        transferAccounts.add(a);
        transferAccounts.add(b);
        if (accounts.containsAll(transferAccounts)) {
            a.withdraw(value);
            b.deposit(value);
        }
    }

    public String getStatement() {
        StringBuilder statementBuilder = new StringBuilder();
        statementBuilder.append("Statement for " + this.name + "\n");

        double total = 0.0;
        for (Account a : accounts) {
            statementBuilder.append("\n" + statementForAccount(a) + "\n");
            total += a.sumTransactions();
        }
        statementBuilder.append("\nTotal In All Accounts " + toDollars(total));
        return statementBuilder.toString();
    }

    private String statementForAccount(Account a) {
        StringBuilder statementBuilder = new StringBuilder();

       //Translate to pretty account type
        switch(a.getAccountType()){
            case CHECKING:
                statementBuilder.append("Checking Account\n");
                break;
            case SAVINGS:
                statementBuilder.append("Savings Account\n");
                break;
            case MAXI_SAVINGS:
                statementBuilder.append("Maxi Savings Account\n");
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.getTransactions()) {
            statementBuilder.append("  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n");
            total += t.amount;
        }
        statementBuilder.append("Total " + toDollars(total));
        return statementBuilder.toString();
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
