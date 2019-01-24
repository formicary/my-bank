package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;

public class Customer {
    private String name;
	private long customerID;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
		generateCustomerID();
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }
	
	private void generateCustomerID(){
		Random rnd = new Random();
		
		this.customerID = rnd.nextLong();
	}
	
	public long getCustomerID(){
		return customerID;
	}

    public Customer openAccount(Account account) {
		if(account.getCustomerID() == -1){
			accounts.add(account);
			account.setCustomerID(this.customerID);
			
			return this;
		} else {
			return null;
		}
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
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    private String statementForAccount(Account a) {
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
        for (Transaction t : a.getTransactions()) {
            s += "  " + (t.getAmount() < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.getAmount()) + "\n";
            total += t.getAmount();
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
}
