package com.abc;

import java.util.ArrayList;
import java.util.List;


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
    
    public Customer transferMoney(int accNo1,int accNo2, double amount){
        if (Math.max(accNo1,accNo2)>=this.accounts.size()){
            throw new IllegalArgumentException("invalid account selected");
        }
        this.accounts.get(accNo1).withdraw(amount,"TRANSFER");
        this.accounts.get(accNo2).deposit(amount,"TRANSFER");
        return this;
    }
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.getInterestSum();
        return total;
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + a.statementForAccount() + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + helper.toDollars(total);
        return statement;
    }
    
    
}
