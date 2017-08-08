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
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + a.getStatement() + "\n";
            // UPDATE: get balance instantly
            total += a.getBalance();  
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    // REMOVED: hanlde by in each account
    // private String statementForAccount(Account a) {
    //     String s = "";

    //    //Translate to pretty account type
    //     switch(a.getAccountType()){
    //         case Account.CHECKING:
    //             s += "Checking Account\n";
    //             break;
    //         case Account.SAVINGS:
    //             s += "Savings Account\n";
    //             break;
    //         case Account.MAXI_SAVINGS:
    //             s += "Maxi Savings Account\n";
    //             break;
    //     }

    //     //Now total up all the transactions
    //     for (Transaction t : a.transactions) {
    //         s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
    //     }
    //     // UPDATE : get balance instantly 
    //     s += "Total " + toDollars(a.getBalance());
    //     return s;
    // }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }

    // UPDATE: change interest rate to a new InteresProvider
    public void updateInterestRate(InterestProvider newInterestRate){
        for(Account a: accounts){
            a.updateInterestRate(newInterestRate);
        }
    }
}
