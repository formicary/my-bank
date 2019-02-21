package com.abc;

import java.util.*;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private Map<UUID, Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new HashMap<UUID, Account>();
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(Account account) {
        accounts.put(account.getAccountID(), account);
        return this;
    }

    public void transfer(UUID from, UUID to, double amount, Date date){
        getAccount(from).withdraw(amount, date);
        getAccount(to).deposit(amount, date);
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned(double turnover) {
        double total = 0;
        for (Account a : accounts.values())
            total += a.interestEarned(turnover);
        return total;
    }

    public String getStatement() {
        String statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts.values()) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    public Account getAccount(UUID id){
        if(accounts.get(id) != null){
            return accounts.get(id);
        }
        else{
            throw new RuntimeException(String.format("account: %1$s does not exist", id.toString()));
        }
    }

    private String statementForAccount(Account a) {
        String s = "";

       //Translate to pretty account type
        if(a instanceof CheckingAccount) s+= "Checking Account\n";
        else if(a instanceof SavingsAccount) s+= "Savings Account\n";
        else if(a instanceof MaxiSavingsAccount) s+= "Maxi Savings Account\n";

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
