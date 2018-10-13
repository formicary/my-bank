package com.abc;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * 
 * @author Accenture, rrana
 * @version v2.0
 *
 */
public class RegularCustomer implements Customer {
    private String name;
    private ArrayList<Account> accounts;
    private Date date;

    /**
     * Constructor
     * @param name the name of the customer
     * @param date the date when this customer joined
     */
    public RegularCustomer(String name, Date date) {
        this.name = name;
        this.date = date;
        this.accounts = new ArrayList<Account>();
    }

	/**
	 * 
	 * @return Name of the customer
	 */
    public String getName() {
        return name;
    }

    /**
	 * Add new account
	 * @param account the account to be added
	 */
    public void addAccount(Account account) {
        accounts.add(account);
    }

    /**
     * 
     * @return number of accounts owned by this customer
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * 
     * @return total interest earned by this customer in all owned accounts
     */
    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.interestEarned();
        return total;
    }

    /**
     * 
     * @return the statement
     */
    public String getStatement() {
        Statement s = new FullStatement(DateProvider.getInstance().now(), this);
        return s.getContent();
    }

	/*
	 * (non-Javadoc)
	 * @see com.abc.Customer#getAccounts()
	 */
	public ArrayList<Account> getAccounts() {
		return accounts;
	}

}
