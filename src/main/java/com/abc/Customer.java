package com.abc;

import java.util.ArrayList;
import java.util.List;
import com.abc.account.AccountI;
import com.abc.account.Checking;
import com.abc.account.MaxiSaving;
import com.abc.account.Saving;
import com.abc.transaction.Transaction;

import static java.lang.Math.abs;

public class Customer {
	
    private String name;
    private List<AccountI> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<AccountI>();
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(AccountI account) {
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (AccountI a : accounts)
            total += a.interestEarned();
        return total;
    }

    public StringBuilder getStatement() {
    	StringBuilder statement=new StringBuilder();
        
        statement .append( "\nStatement for " ).append(name).append("\n");
        double total = 0.0;
        for (AccountI a : accounts) {
        
        	statement.append("\n").append(statementForAccount(a)).append("\n");
            total += a.sumTransactions();
        }
      
        statement.append("\nTotal In All Accounts ").append( toDollars(total));
        return statement;
    }

    private StringBuilder statementForAccount(AccountI a) {
       StringBuilder s = new StringBuilder();
    	
        if (a instanceof Checking) {
        	s .append("Checking Account\n"); //
        	
        	
        } else if (a instanceof Saving) {
        	
        	s .append("Savings Account\n");
        	
        } else if (a instanceof MaxiSaving) {
        	s .append( "Maxi Savings Account\n");
        	
        }
        
        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.getTransactions()) {
           
        	s.append( "  ").append((t.getAmount() < 0 ? "withdrawal" : "deposit") ).append(" ").append(toDollars(t.getAmount())).append("\n");
            total += t.getAmount();
        }
        
        s.append( "Total ").append(toDollars(total));
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
