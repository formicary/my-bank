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

    public void openAccount(Account account) {
        accounts.add(account);
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

    /**
     * Summarises the customers accounts.
     * @return the summary
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
     * Summarizes an account
     * @param account - the account to be summarized
     * @return
     */
    private String statementForAccount(Account account) {
        String s = "";

        switch(account.getAccountType()){
            case CHECKING:
                s += "Checking Account\n";
                break;
            case SAVING:
                s += "Savings Account\n";
                break;
            case MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : account.transactions) {
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
     * Transfers money from one account to another.
     * @param fromAccountNum - the account the money is being taken from
     * @param toAccountNum - the account the money is going to 
     * @param amount - the amount of money being transfered
     */
    public void transferMoney(int fromAccountNum, int toAccountNum, double amount){

    	if((fromAccountNum == toAccountNum) || amount < 0){
    		return;
    	}
    	
    	Account fromAccount = getAccountByNumber(fromAccountNum);
    	Account toAccount = getAccountByNumber(toAccountNum);
    	
    	if((fromAccount != null) && (toAccount != null)){
    		
    		fromAccount.withdraw(amount);
    		toAccount.deposit(amount);
    	} 
    }
    
    /**
     * Find and return the account with the specified number.
     * @param accNum - the account number
     * @return the account with the specified number.
     */
    public Account getAccountByNumber(int accNum){
    	Account accountWithNum = null;
    	
    	for(Account acc : accounts){	
    		if(acc.accountNum == accNum){
    			accountWithNum = acc;
    			break;
    		}
    	}
    	return accountWithNum;
    }
}
