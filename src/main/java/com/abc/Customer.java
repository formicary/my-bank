package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;

    /**
     * Customer is represented by their name and the list of accounts they own.
     * @param name 
     */
    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }

    /**
     * Add a new account for the customer
     * @param account - the account to add
     * @return - the customer as an object
     */
    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    /**
     * Get the number of accounts a customer owns
     * @return the number of accounts as an integer
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * Loop through all accounts and sum up the total interest earned
     * @return 
     */
    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }
    

    /**
     * Print a statement, for each account show the sum of transactions
     * @return - the Printed statement
     */
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

    /**
     * Format the statement
     * @param a - account to query
     * @return - a formatted string
     */
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
        for (Transaction t : a.getTransactions()) {
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
    
     /**
     * Transfer money to another account
     * @param otherAccount 
     */
    public void transferToOtherAccount(Account fromAccount, Account otherAccount, double amount){
         fromAccount.withdraw(amount);
         otherAccount.deposit(amount);
    }

}
