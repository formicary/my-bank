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

    public void transferMoneyBetweenAccounts(int accountFrom, int accountTo, double amount) {
        if (amount <= 0) {throw new IllegalArgumentException("amount must be greater than zero");}
        if (accounts.size() <= 1) {throw new IllegalArgumentException("you don't have 2 open accounts at the moment!");}

        // run the transaction
        accounts.get(accountFrom).withdraw(amount, false);
        accounts.get(accountTo).deposit(amount, false);
    }

    public List<Account> getAccounts(){
        return accounts;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned(true);
        return total;
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
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
        double total = 0.0;
        for (Transaction t : a.transactions) {
            switch(t.transactionType) {
                case 0:
                    s += "  withdrawal " + toDollars(t.amount) + "\n";
                    break;
                case 1:
                    s += "  deposit " + toDollars(t.amount) + "\n";
                    break;
                case 2:
                    if(t.amount >= 0)
                        s += "  transfer " + toDollars(t.amount) + "\n";
                    else
                        s += "  transfer -" + toDollars(t.amount) + "\n";
                    break;
            }

            total += t.amount;
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
