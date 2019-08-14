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

    public void openAccount(Account account) {
        this.accounts.add(account);
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public Money totalInterestEarned() {
        Money total = new Money("0.00");
        for (Account a : accounts)
        	//TODO - Not scalable - needs revision
            total = new Money(total.getAmount().add(a.interestEarned().getAmount()));
        return total;
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        Money total = new Money("0.00");
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            //TODO - Not scalable - needs revision
            total = new Money(total.getAmount().add(a.sumTransactions().getAmount()));
        }
        statement += "\nTotal In All Accounts " + total.getAmount();
        return statement;
    }

    private String statementForAccount(Account a) {
        String s = "";
        // Get the polymorphic account type from parent class
        s += a.getAccountType();

        //Now total up all the transactions
        Money total = new Money("0.00");
        for (Transaction t : a.transactions) {
            s += "  " + (t.getMoney().getAmount().compareTo(Money.ZERO_VALUE) == -1  ? "withdrawal" : "deposit") + " " + t.getMoney().getAmount() + "\n";
            //TODO - Not scalable - needs revision
            total = new Money(total.getAmount().add(t.getMoney().getAmount()));
        }
        s += "Total " + total.getAmount();
        return s;
    }
    
    public String getName() {
        return name;
    }

	public List<Account> getAccounts() {
		return accounts;
	}
}
