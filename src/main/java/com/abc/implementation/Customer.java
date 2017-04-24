package com.abc.implementation;

import java.util.ArrayList;
import java.util.List;

import com.abc.interfaces.IAccount;
import com.abc.interfaces.ICustomer;
import com.abc.interfaces.ITransaction;

import static java.lang.Math.abs;

public class Customer implements ICustomer{
    private String name;
    private List<IAccount> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<IAccount>();
    }

    public String getName() {
        return name;
    }

    public ICustomer openAccount(IAccount account) {
        accounts.add(account);
        return this;
    }
    
    public boolean TransferFunds(IAccount from, IAccount to, double amount)
    {    	
    	if(from == null || to == null) {
    		return false;
    	}
    	if(!accounts.contains(from)|| !accounts.contains(to)) {
    		return false;
    	}
    	
    	if(from.getTotalBalance() < amount || amount <= 0) {
    		return false;
    	}
    	
    	to.deposit(amount);
    	from.withdraw(amount);
    	
    	return true;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (IAccount a : accounts)
            total += a.compoundInterestEarned();
        return total;
    }

    public String getStatement() {
        StringBuilder statement = new StringBuilder("");
        statement.append("Statement for ");
        statement.append(name);
        statement.append("\n");
        double total = 0.0;
        for (IAccount a : accounts) {
        	statement.append("\n");
        	statement.append(statementForAccount(a));
        	statement.append("\n");
            total += a.getTotalBalance();
        }
        statement.append("\nTotal In All Accounts ");
        statement.append(toDollars(total));
        return statement.toString();
    }

    private String statementForAccount(IAccount account) {
        StringBuilder result = new StringBuilder("");

       //Translate to pretty account type
        switch(account.getAccountType()){
            case CHECKING:
            	result.append("Checking Account\n");
                break;
            case SAVINGS:
            	result.append("Savings Account\n");
                break;
            case MAXI_SAVINGS:
            	result.append("Maxi Savings Account\n");
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        ITransaction transaction = account.getRootTransaction();
        while (transaction != null) {
        	result.append("  ");
        	result.append(transaction.getAmount() < 0 ? "withdrawal" : "deposit"); 
        	result.append(" ");
        	result.append(toDollars(transaction.getAmount()));
        	result.append("\n");
            total += transaction.getAmount();
            transaction = transaction.getNextTransaction();
        }
        result.append("Total ");
        result.append(toDollars(total));
        return result.toString();
    }

    private String toDollars(double amout){
        return String.format("$%,.2f", abs(amout));
    }
}
