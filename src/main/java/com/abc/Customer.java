package com.abc;

import java.util.ArrayList;
import java.util.List;

/* Written by Tunc Demircan */
public class Customer {

    private String name;
    private List<Account> accounts;
    private int numberOfAccounts;

    public Customer(String name){
        this.name = name;
        this.accounts = new ArrayList<Account>();
        this.numberOfAccounts = 0;
    }

    // Returns a String statement. Usable even if the customer has no accounts/transactions
    public String getStatement(){
        String fullStatement = "Statement for " + name;
        double total = 0.0;

        if(accounts.isEmpty()){
            fullStatement += "\nNo accounts to display.";
            return fullStatement;
        }

        for (Account account : accounts) {
            String statement = "";
            statement += "\n\n" + account.accountTypeName() + "\nBalance: " +
                    String.format("$%,.2f", account.getBalance()) + "\nTransactions:";
            if(account.getTransactions().isEmpty()){
                statement += "\nNo transactions to display";
            } else {
                for (Transaction transaction : account.getTransactions()) {
                    statement += "\n" + String.format("$%,.2f", transaction.getAmount()) + " " + transaction.getType();
                }
            }
            fullStatement += statement + "\n\n";
        }
        return fullStatement;
    }


    public void incrementNumberOfAccounts(){
        numberOfAccounts += 1;
    }

    // Getters

    public String getName() {
        return name;
    }

    public int getNumberOfAccounts() {
        return numberOfAccounts;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

}
