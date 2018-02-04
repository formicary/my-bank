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
    /**
     * Method to open a customer account. 
     * @param account
     * @return
     */
    public Customer openAccount(Account account) {
    	int lastAccountId;
    	//set the account ID
    	if(getNumberOfAccounts()==0){
    		lastAccountId=-1;
    	}else{
    		lastAccountId = accounts.get(accounts.size()-1).getAccountId();
    	}
    	Account accountWithId = new Account(account.getAccountType(), lastAccountId+1);
    	accounts.add(accountWithId);
        return this;
    }
    /**
     * Method to return a specific account based on its id
     * @param id
     * @return account
     * @throws IllegalArgumentException
     */
    public Account getAccount(int id) throws IllegalArgumentException{
	   for(Account account: accounts){
		   if(account.getAccountId()==id){
			   return account;
		   }
	   }
	   throw new IllegalArgumentException("There is no account with id "+id);
    }
    public int getNumberOfAccounts() {
        return accounts.size();
    }
	/**
	 * Method to return the total interest earned on all accounts
	 * @return
	 */
    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }
    /**
     * Method to build a customer statement with information about the accounts and total funds.
     * @return the statement
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
     * Helper method that returns  information about one account
     * @param a
     * @return statement
     */
    private String statementForAccount(Account a) {
        String statement = "";

       //Translate to pretty account type
        switch(a.getAccountType()){
            case Account.CHECKING:
            	statement += "Checking Account\n";
                break;
            case Account.SAVINGS:
            	statement += "Savings Account\n";
                break;
            case Account.MAXI_SAVINGS:
            	statement += "Maxi Savings Account\n";
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        List<Transaction> transactions = a.getTransactions();
        for (Transaction t : transactions) {
        	statement += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
            total += t.amount;
        }
        statement += "Total " + toDollars(total);
        return statement;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
    /**
     * A method to allow a customer to transfer funds between their accounts.
     * @param a
     * @param b
     * @param amount
     * @throws IllegalArgumentException
     */
    public void transferBetweenAccounts(Account creditedAccount, Account debitedAccount, double amount) throws IllegalArgumentException {
    	//optional error handling if credit / overdraft is not permitted
    	//if (a.getBalance()-amount >0){
    	//	throw new IllegalArgumentException("The withdrawing account does not have the required funds for this operation!");
    	//}
    	if (creditedAccount.getAccountId()== debitedAccount.getAccountId()){
    		throw new IllegalArgumentException("The withdrawing account cannot be the same as the depositing account!");
    	}
    	creditedAccount.withdraw(amount);
    	debitedAccount.deposit(amount);
    }
}
