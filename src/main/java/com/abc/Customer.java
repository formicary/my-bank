package com.abc;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;
/*A class to contain Customer information: name and accounts


@author Hans-Wai Chan

*/
public class Customer {
    private String name;
    private List<Account> accounts;

	/**    
	* Constructor which initialises with name and creates an empty list for Accounts
	*    
	*   @param name 
	*    	String: name of the customer
	*/
    
    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }
 
	 /**
	  * Gets the name of the customer   
	  * @return String: customer
	  */
    public String getName() {
        return name;
    }

	/**    	
	 * Method to add a new account for Customer
	 * 
	 * @param account
	 *  	(Object) Account: The account of the customer to be added.
	 * @returns this (Object) Customer		
	 */
    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    /**
     * Method gets the number of accounts for this customer.
     * 
     * @return Integer: number of accounts of this customer.
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * Method to calculate the total interest earned across ALL accounts of the customer
     * 
     * @return double: total interested earned across ALL account.
     */
    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

	/**
	 * Method to get statement for ALL accounts of the customer. Statement contains each statement for each account and the total.
	 * 
	 * @return String: statement
	 */
    public String getStatement() {
        String statement = "";
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
     * Method to get statement for one account of the customer. Statement contains each transaction and the total.
     * 
     * @param a
     * 		Account: account containing information for statement
     * @return
     * 		String: Information of statement.
     */
    private String statementForAccount(Account a) {
        String s = "";

       //Translate to pretty account type
        if(a instanceof CheckingAccount)
	       s += "Checking Account\n";
	    else if(a instanceof SavingsAccount)
	        s += "Savings Account\n";
	    else if(a instanceof MaxiSavingsAccount)
	        s += "Maxi Savings Account\n";

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.getTransaction()) {
            s += "  " + (t.getAmount() < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.getAmount()) + "\n";
            total += t.getAmount();
        }
        s += "Total " + toDollars(total);
        return s;
    }

	/**
	 * Method to convert double to a format of dollars
	 * 
	 * @param d Double that will be converted to dollars.
	 * @return String 
	 */
    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
    
    /**
     * Method to transfer amount from account to account. 
     * @param from
     * 		(Object) Account: account to be withdrawn for transfer
     * @param to
     * 		(Object) Account: account to receive from transfer
     * @param amount
     * 				double : amount for transfer.
     * @return
     * 		Boolean: returns true if transaction is successful - Both accounts exists to make transaction.
     * 						else false
     */
	public boolean transferMoney(Account from, Account to, double amount) {
			if (accounts.contains(from) && accounts.contains(to) && from.sumTransactions() > amount) {
				from.withdraw(amount);
				to.deposit(amount);
				return true;
			}
			return false;
		}
	
	
}
