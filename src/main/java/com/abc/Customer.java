package com.abc;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
            total += a.getBalance();
        }
        // statement += "\nTotal In All Accounts " + toDollars(total);	// ORIGINAL LINE
        statement += "\n" + String.format("%-23s: %23s", "Total In All Accounts", toDollars(total));
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

        for (Transaction t : a.transactions) {
            // s += "  " + (t.amount < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.amount) + "\n";	// ORIGINAL LINE
            s += toDate(t.getTransactionDate()) + "  " + ((t.amount < 0) ? "Withdrawal : " : "Deposit    : ");
            s += toDollars(t.amount) + "  |" + toDollars(t.newBalance) + "\n";
        }
        // s += "Total " + toDollars(total);
        s += String.format("%-23s: %23s", "Current Balance", toDollars(a.getBalance()));
        return s;
    }

    private String toDollars(double d){
        // return String.format("$%,.2f", abs(d));	// ORIGINAL LINE
    	String dollarFormat = String.format("$%,.2f", abs(d));
        return String.format("%10s", dollarFormat);
    }
    
	private String toDate(Date d) {
    	String dateFormat = "dd/MM/yyyy";
    	SimpleDateFormat s = new SimpleDateFormat(dateFormat);
    	return s.format(d);
    }
}
