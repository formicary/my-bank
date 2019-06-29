package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;

    protected final String uniqueID;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
        this.uniqueID = UUID.randomUUID().toString();
    }

    public String getName() {
        return name;
    }

    public Account openCheckingAccount(){
        CheckingAccount acc = new CheckingAccount(this);
        accounts.add(acc);
        return acc;
    }

    public Account openMaxiSavingsAccount(){
        MaxiSavingsAccount acc = new MaxiSavingsAccount(this);
        accounts.add(acc);
        return acc;
    }

    public Account openSavingsAccount(){
        SavingsAccount acc = new SavingsAccount(this);
        accounts.add(acc);
        return acc;
    }

    public void transferFunds(Account sender, Account recipient, double amount){
        Transfer.newTransfer(amount, sender, recipient);
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0.0;

        for (Account a : accounts) total += a.interestEarnedAnnum();
        return total;
    }

    public String getStatement() {
        StringBuilder statement = new StringBuilder("Statement for " + this.name + "\n");

        double total = 0.0;

        for (Account a : accounts) {
            statement.append("\n").append(a.getAccountStatement()).append("\n");
            total += a.getAccountBalance();
        }

        statement.append("\nTotal In All Accounts ").append(CurrencyFormat.toDollars(total));
        return statement.toString();
    }
}
