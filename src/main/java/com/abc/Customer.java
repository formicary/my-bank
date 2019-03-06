package com.abc;

import static java.lang.Math.abs;

import java.util.ArrayList;
import java.util.List;

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
    
    public List<Account> getAccounts() {
		return accounts;
	}

	public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }
	
	public void transferFunds(Account accountTransferFrom, Account accountTransferTo, double amount) {
		if(accountTransferFrom.equals(accountTransferTo)) {
			throw new IllegalArgumentException("Cannot transfer funds between the same account"); 
		}
		
		accountTransferFrom.withdraw(amount);
		accountTransferTo.deposit(amount);
	}

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account account : accounts)
            total += account.interestEarned();
        return total;
    }

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

    private String statementForAccount(Account account) {
        String s = "";

       //Translate to pretty account type
        switch(account.getAccountType()){
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
        for (Transaction transaction : account.transactions) {
            s += "  " + (transaction.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(transaction.amount) + "\n";
            total += transaction.amount;
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
