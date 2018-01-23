package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private final String name;
    private final List<Account> accounts;

    //cosntructor
    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    //method to retrieve the name of a customer
    public String getName() {
        return name;
    }

    //method to open an account for a customer
    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    //method to get the number of accounts a customer has
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    //method to sum up all the interest earned over all the accounts a customer has
    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    //method to get a summarising statement for a customer
    public String getStatement() {
        String statement;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    //method to get the text for the summary statement for different types of accounts
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
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        s += "Total " + toDollars(total);
        return s;
    }

    //method to format the given double into dollars
    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
    
    //method that allows the customer to perform transfers between accounts
    public void betweenAccountTransaction(Account aAccount, Account bAccount, double amount){
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero.");
        } else {
            aAccount.withdraw(amount);
            bAccount.deposit(amount);
        }
    }
}
