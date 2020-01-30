package com.abc;

import java.util.ArrayList;
import java.util.List;
import static java.lang.Math.abs;


public class Customer {
    
	private String name;
    private List<Account> accounts;
    
    //A customer is created with its name and an empty list of accounts.
    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }
    
    //Access name of customer.
    public String getName() {
        return name;
    }
    
    //Access customer's accounts.
    public List<Account> getAccounts(){
    	return accounts;
    }
    
    //Open an account.
    public void openAccount(Account account) {
        accounts.add(account);
    }
    
    //Count the number of accounts a customer has.
    public int getNumberOfAccounts() {
        return accounts.size();
    }
    
    //Generate statement for customer with the name and transactions for each account, as well as the total amount of money. 
    public String getStatement() {
        String statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }
    
    //Generate statement for different types of accounts.
    private String statementForAccount(Account a) {
        String s = "";
       //Translate to pretty account type
        switch(a.getAccountType()){
            case 0:
                s += "Checking Account\n";
                break;
            case 1:
                s += "Savings Account\n";
                break;
            case 2:
                s += "Maxi Savings Account\n";
                break;
        }
        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.getTransactions()) {
            s += "  " + (t.getAmount() < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.getAmount()) + "\n";
            total += t.getAmount();
        }
        s += "Total " + toDollars(total);
        return s;
    }

    //Add dollar sign to amount. 
    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
