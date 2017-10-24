package com.abc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.math.BigDecimal;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;

    private Calendar now;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
        this.now = Calendar.getInstance();
        this.now.setTimeInMillis(System.currentTimeMillis());
    }

    public Customer(String name, Calendar date) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
        this.now = date;
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

    public BigDecimal totalInterestEarned(boolean realtime) {
        BigDecimal total = BigDecimal.ZERO;
        for (Account a : accounts)
            total = total.add(a.interestEarned(realtime));
        return total;
    }

    public String getStatement(boolean realtime, boolean suminterest) {
        String statement = null;
        statement = "Statement for " + name + "\n";
        BigDecimal total = BigDecimal.ZERO;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a,realtime,suminterest) + "\n";
            total = total.add(a.sumTransactions());
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

	public void transfer(Account sourceaccount, Account targetaccount, BigDecimal amount, boolean realtime){
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("amount must be greater than zero");
        } else {
			sourceaccount.withdraw(amount,realtime);
			targetaccount.deposit(amount,realtime);
        }
	
	}

    private String statementForAccount(Account a, boolean realtime, boolean suminterest) {
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
        BigDecimal total = BigDecimal.ZERO;
		List<Transaction> transactions = a.getTransactions(realtime);
        for (Transaction t : transactions) {
			if (!t.interest){
                s += "  " + (t.amount.compareTo(BigDecimal.ZERO) < 0 ? "withdrawal" : "deposit   ") + " " + toDollars(t.amount) + "\n";
			} else if (!suminterest && t.interest) {
                s += "  interest $" + String.format("$%,.6f", t.amount.doubleValue()) + "\n";
            }
            total = total.add(t.amount);
        }
        s += "  total interest " + toDollars(a.interestEarned(realtime)) + "\n";
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(BigDecimal d){
        return String.format("$%,.2f", d.abs());
    }

    public void setDate(Calendar date){
        this.now = date;
        for (Account t : accounts){
            t.setDate(date);
        }
    }
}
