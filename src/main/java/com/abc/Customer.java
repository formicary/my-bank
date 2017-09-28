package com.abc;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;


public class Customer {
    private String name;
    private List<Account> accounts;
    
    /**
     * constructor that creates a new list of accounts the customer has
     * @param name 
     */
    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }
    
    /**
     * 
     * @return name of customer
     */
    public String getName() {
        return name;
    }
    
    /**
     * 
     * @param account
     * @return new account for the customer
     */
    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }
    
    /**
     * 
     * @return number of accounts
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }
    
    /**
     * 
     * @return total interest earned
     */
    public double totalInterestEarned() {
        double total = 0;
        for (Account account : accounts)
            total += account.interestEarned();
        return total;
    }
    
    /**
     * 
     * @return statement for customer
     */
    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account account : accounts) {
            statement += "\n" + statementForAccount(account) + "\n";
            total += account.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    /**
     * 
     * @param account
     * @return statement for account
     */
    private String statementForAccount(Account account) {
        String string = "";

       //Translate to pretty account type
        switch(account.getAccountType()){
            case Account.CHECKING:
                string += "Checking Account\n";
                break;
            case Account.SAVINGS:
                string += "Savings Account\n";
                break;
            case Account.MAXI_SAVINGS:
                string += "Maxi Savings Account\n";
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        
        for (Transaction transaction : account.getTransactions()) {
            string += "  " + (transaction.getAmount() < 0 ? "withdrawal" : "deposit") + " " + toDollars(transaction.getAmount()) + "\n";
            total += transaction.getAmount();
        }
        string += "Total " + toDollars(total);
        return string;
    }
    
    /**
     * 
     * @param dollars
     * @return string of the dollars
     */
    private String toDollars(double dollars){
        return String.format("$%,.2f", abs(dollars));
    }
}
