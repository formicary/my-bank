package com.abc;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author fearg
 *
 */
public class Customer {

	private String name;
    private List<Account> accounts;

    /**
     * constructor that takes name as arg
     * @param name
     */
    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    /**
     * getter for name
     * @return
     */
    public String getName() {
        return name;
    }

   /**
    * open account takes an account as an arg and adds it to customer's list
    * @param account
    */
    public void openAccount(Account account) {
        accounts.add(account);
    }
    
    /**
     * returns number of accounts belonging to the customer
     * @return
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }
    
    /**
     * if customer does own both accounts then the speicifed amount will be withdrawn from one
     * and deposited in the other
     * @param accountFrom
     * @param accountTo
     * @param amount
     */
    public void transferBetweenAccounts(Account accountFrom, Account accountTo, double amount) {
    	if(accounts.contains(accountFrom) && accounts.contains(accountTo)) {
    		accountFrom.withdraw(amount);
    		accountTo.deposit(amount);
    	}
    }

    /**
     * adds interest from all accounts
     * @return
     */
    public double totalInterestEarned() {
        double total = 0;
        
        for (Account a : accounts) {
            total += a.interestEarned();
        }
        return total;
    }

    /**
     * returns a string that can be interpreted as the customer's statement
     * @return
     */
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

    /**
     * 
     * @param a
     * @return
     */
    private String statementForAccount(Account a) {
        String s = "";
        double running_total = 0.0;

       //Translate to pretty account type
        switch(a.getAccountType()){
            case SAVINGS:
                s += "Savings Account\n";
                break;
            case MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
            default:
            	s += "Checking Account\n";
                break;
        }

        //Now total up all the transactions
        for (Transaction t : a.transactions) {
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            running_total += t.amount;
        }
        
        s += "Total " + toDollars(running_total);
        return s;
    }

    /**
     * convert the double to dollar format
     * @param d
     * @return
     */
    private String toDollars(double d){
        return String.format("$%,.2f", Math.abs(d));
    }
    
}
