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
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned(Account.YEARLY);	//Used to check that interest rates are calculated correctly.
        return total;
    }

    public void transferBetweenAccounts(Account accountTransferredFrom, Account accountTransferredTo, double amount) {
    	if (amount <= 0) {
	        throw new IllegalArgumentException("amount must be greater than zero");	//Check a positive amount is being transferred
    	}
	        
    	accountTransferredFrom.withdraw(amount);	//transferring is shown on the account as withdrawing and depositing into another account
    	accountTransferredTo.deposit(amount);
    }
    
	public void updateInterestForAccounts() {
		for (Account a : accounts)
			a.accrueAndCompountInterest();	//Update the balance of all accounts using daily interest
			
	}
    
    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.getAccountBalance();	//Now adds up account balance
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
        for (Transaction t : a.transactions) {
            s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";
        }
        
        s += "Total " + toDollars(a.getAccountBalance());	//Adding up total changed now done with a balance variable
        
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
