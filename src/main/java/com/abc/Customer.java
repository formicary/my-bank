package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;
    private double accountsTotal;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
        this.accountsTotal = 0;
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(Account account) {
        if(account.validateData()) {
            accounts.add(account);
            accountsTotal += account.getAmount();
            return this;
        } else {
            throw new IllegalArgumentException("Invalid account");
        }
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
        String statement = null;
        statement = "Statement for " + name + "\n";

        if(accounts.size()==0) {
            statement += name+" has no accounts.";
        } else {
            for (Account a : accounts) {
                statement += "\n" + statementForAccount(a) + "\n";
            }
            statement += "\nTotal In All Accounts " + toDollars(this.accountsTotal);
        }
        return statement;
    }

    private String statementForAccount(Account a) {
        String s = "";

       //Translate to pretty account type
        switch(a.getAccountType()){
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
        for (Transaction t : a.transactions) {
            s += "  " + (t.getAmount() < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.getAmount()) + "\n";
        }
        s += "Total " + toDollars(a.getAmount());
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }

    public boolean validateData() {
        if(name=="" || name==null) {
            return false;
        }
        return true;
    }
}
