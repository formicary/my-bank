package com.abc;

import com.sun.org.apache.xpath.internal.operations.Bool;

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

    public Customer openAccount(Account account) {//Multiple accounts of the same type
        for(Account a: this.accounts){
            if(a.getAccountType() == account.getAccountType()){
                throw new IllegalArgumentException("Cannot have more than one of each account");
            }
        }
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarnedDaily() {
        double total = 0;
        for (Account a : accounts)
            total += a.dailyInterestEarned();
        return total;
    }

    public String getStatement() {
        StringBuilder statement = new StringBuilder();
        statement.append("Statement for ");
        statement.append(name);
        statement.append("\n");
        double total = 0.0;
        for (Account a : accounts) {
            statement.append("\n");
            statement = (statementForAccount(a, statement));
            statement.append("\n");
            total += a.sumTransactions();
        }
        statement.append("\nTotal In All Accounts ");
        statement.append(toDollars(total));
        return statement.toString();
    }

    public Boolean transferFunds(Account transferFrom, Account transferTo, Double amount){
        if(transferFrom.withdraw(amount)){
            transferTo.deposit(amount);
            return true;
        }else {
            return false;
        }
    }

    private StringBuilder statementForAccount(Account a, StringBuilder s) {
       //Translate to pretty account type
        switch(a.getAccountType()){
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
            s.append("  ");
            s.append(t.note);
            s.append(" ");
            s.append(toDollars(t.amount));
            s.append("\n");
            total += t.amount;
        }
        s.append("Total ");
        s.append(toDollars(total));
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
