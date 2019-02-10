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
            total += a.interestEarned();
        return total;
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts: " + toDollars(total);
        return statement;
    }

    // Switched to stringbuilder
    private String statementForAccount(Account a) {
        StringBuilder statement = new StringBuilder();

       //Translate to pretty account type
        switch(a.getAccountType()){
            case CHECKING:
            	statement.append("Checking Account\n");
                break;
            case SAVINGS:
            	statement.append("Savings Account\n");
                break;
            case MAXI_SAVINGS:
            	statement.append("Maxi Savings Account\n");
                break;
        }

        //Building up account statement report
        double total = 0.0;
        for (Transaction t : a.transactions) {
            statement.append("  ");
            statement.append(t.amount < 0 ? "withdrawal" : "deposit");
            statement.append(" ");
            statement.append(toDollars(t.amount));
            statement.append("\n");
            
            total += t.amount;
        }
        statement.append("Total " + toDollars(total));
        return statement.toString();
    }

    // Keeps formatting after intrest
    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
    
    public Account getCustomerAccounts(int accountNo) {
        return accounts.get(accountNo);
    }
    
    // Transfer between accounts
    public void Transfer(double amount, Account a, Account b){
    	if(a.sumTransactions() < amount){
    		throw new IllegalArgumentException("Insufficient funds.");
    	}
    	else if(!(accounts.contains(a) && accounts.contains(b))){
    		throw new IllegalArgumentException("Account(s) does not exist.");
    	}
    	else{
    		a.withdraw(amount);
    		b.deposit(amount);
    	}
    }
}