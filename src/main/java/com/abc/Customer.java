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
        /*If the customer opens multiple accounts of the same type,
          then there must either be an account ID to identify an account
          or the customer should be restricted to one of each account type.
        
        my implementation will restrict a customer to a having a maximum of one 
        of each account types.
        */
        
        //if account type exists, throw exception
        for (Account a: accounts){
            if(a.getAccountType()==account.getAccountType()){  
                throw new IllegalArgumentException("Already have that account type"); 
            }
        }
        
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
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        s += "Total " + toDollars(total);
        return s;
    }
    
    private String toDollars(double d){
        if (d<0){
            
         return String.format("-$%,.2f", abs(d));   
        }else{
        return String.format("$%,.2f", abs(d));
        }
    }
    
    
    public boolean accountExists(int acc){
        //checking if the accounts exist
        //try{
        for (Account a: accounts){
            if(a.getAccountType()==acc){  
            return true;
            }
        }
        return false;
    }
    // takes the customers accounts to withdraw from and deposit to and the amount, check if accounts exist, then withdraw and deposit
    public void transfer(int to, int from, double amount){
        if (amount <= 0) {
        throw new IllegalArgumentException("amount must be greater than zero");
    } else {
            
            if (accountExists(to) == true && accountExists(from)){
                //customer has both accounts.
                //withdraw and deposit 
                for (Account a: accounts){
                    if(a.getAccountType()==to){  
                        a.deposit(amount);
                    }
                    if(a.getAccountType()==from){  
                        a.withdraw(amount);
                    }
                }
            
                
            }else {
                //one or both accounts do not exist
                throw new IllegalArgumentException("account type does not exist");
            }
        }
        
    }
}
