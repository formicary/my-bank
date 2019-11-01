package com.abc;

import static java.lang.Math.abs;
import java.util.HashMap;

/**
 * Changed the List of accounts to HashMap, allowing only one account type per customer.
 * Changed openAccount method to check if an account of the same type exists.
 * Added a method that returns the Account object given its type.
 * Changed statementForAccount to public to return statement for specific account type.
 * Added transferBetweenAccounts method to allow a customer to transfer between accounts.
 * @author Andreas Neokleous
 */
public class Customer {
    private String name;
    private HashMap<Integer, Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new HashMap<Integer, Account>();
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(Account account) {
        if (accounts.containsKey(account.getAccountType())){
            throw new IllegalArgumentException("Account of same type exists.");
        }
        accounts.put(account.getAccountType(), account);
        return this;
    }
    
    public Account getAccount(int accountType){
        if (this.accounts.containsKey(accountType)){
            return this.accounts.get(accountType);
        }else{
            throw new IllegalArgumentException("Account does not exists.");
        }
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts.values())
            total += a.interestEarned();
        return total;
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts.values()) {
            statement += "\n" + getStatementForAccount(a.getAccountType()) + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

  

    public String getStatementForAccount(int accountType) {
        String s = "";

       //Translate to pretty account type
        switch(accountType){
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
        for (Transaction t : accounts.get(accountType).transactions) {
            s += "  " + (t.getAmount() < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.getAmount()) + "\n";
            total += t.getAmount();
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
    
    public void transferBetweenAccounts(int accountFrom, int accountTo, double amount){
        if (this.accounts.containsKey(accountFrom) && this.accounts.containsKey(accountTo)){
            if (amount>0){
                Account from = this.accounts.get(accountFrom);
                Account to = this.accounts.get(accountTo);
                from.withdraw(amount);
                to.deposit(amount);
            }
        }else{
            throw new IllegalArgumentException("Accounts do not exist.");
        }
    }
}
