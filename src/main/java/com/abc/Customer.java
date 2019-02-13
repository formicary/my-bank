package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;


    Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public void openAccount(Account account) {
        accounts.add(account);
    }

    public double totalInterestEarned(boolean forYear) {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned(forYear);
        return total;
    }

    public String getStatement() {
        StringBuilder statement = new StringBuilder("Statement for " + name + "\n");
        double total = 0.0;

        for (Account a : accounts) {
            statement.append("\n");
            statement.append(statementForAccount(a));
            statement.append("\n");
            total += a.getBalance();
        }
        statement.append("\n");
        statement.append("Total In All Accounts ");
        statement.append(toDollars(total));

        return statement.toString();
    }

    //Build Statement String
    private String statementForAccount(Account a) {
        StringBuilder s = new StringBuilder();

        //Change to Textual Account Type
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

        double total = 0.0;

        //Change to Textual Statement
        for (Transaction t : a.getTransactions()) {
            double amount = t.getAmount();
            s.append("  ");
            s.append(amount < 0 ? "withdrawal" : "deposit");
            s.append(" ");
            s.append(toDollars(amount));
            s.append("\n");
            total += amount;
        }
        s.append("Total ");
        s.append(toDollars(total));
        return s.toString();
    }

    public boolean transferBetweenAccounts(Account from, Account to, double amount){
        //Check Customer Owns Both Accounts
        if(!accounts.contains(from) || !accounts.contains(to))
            return false;

        //Check Balance
        if(from.getBalance() < amount)
            return false;

        //Transfer Funds
        from.withdraw(amount);
        to.deposit(amount);
        return true;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
