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
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (IAccount a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.getTotalBalance();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    private String statementForAccount(IAccount a) {
        String s = "";

       //Translate to pretty account type
        switch(a.getAccountType()){
            case CHECKING:
                s += "Checking Account\n";
                break;
            case SAVINGS:
                s += "Savings Account\n";
                break;
            case MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        ITransaction transaction = a.getRootTransaction();
        while (transaction != null) {
            s += "  " + (transaction.getAmount() < 0 ? "withdrawal" : "deposit") + " " + toDollars(transaction.getAmount()) + "\n";
            total += transaction.getAmount();
            transaction = transaction.getNextTransaction();
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
