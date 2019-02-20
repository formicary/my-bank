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
    
    public void transferBetweenAccounts(double amount, Account a, Account b) {
		if (amount <= 0) {
			throw new IllegalArgumentException("amount must be greater than zero");
		} else if (!accounts.contains(a) || !accounts.contains(b)) {
			throw new IllegalArgumentException("account does not exist");
    	} else {
			a.withdraw(amount);
			b.deposit(amount);
		}
	}

    public String getStatement() {
    	StringBuffer s = new StringBuffer();
        s.append("Statement for " + name + "\n");
        double total = 0.0;
        
        for (Account a : accounts) {
            s.append("\n" + statementForAccount(a) + "\n");
            total += a.sumTransactions();
        }
        
        s.append("\nTotal In All Accounts " + toDollars(total));
        
        return s.toString();
    }

    private String statementForAccount(Account a) {
        StringBuffer s = new StringBuffer();

       //Translate to pretty account type
        switch(a.getAccountType()){
            case Account.CHECKING:
                s.append("Checking Account\n");
                break;
            case Account.SAVINGS:
                s.append("Savings Account\n");
                break;
            case Account.MAXI_SAVINGS:
                s.append("Maxi Savings Account\n");
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.transactions) {
            s.append("  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n");
            total += t.amount;
        }
        
        s.append("Total " + toDollars(total));
        
        return s.toString();
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
