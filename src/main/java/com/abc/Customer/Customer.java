package com.abc.Customer;

import com.abc.Account.Account;

import com.abc.Money;;
import java.util.ArrayList;

import static java.lang.Math.abs;

/**
 * This class stores a reference to a customers accounts and provides helper functions for aggregate statistics
 */
public class Customer {
    private String name;
    private ArrayList<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    /**
     * @return customers given/legal name
     */
    public String getName() {
        return name;
    }

    /**
     * Add account to customers portfolio
     * @param account
     */
    public void addAccount(Account account) {
        accounts.add(account);
    }

    /**
     * @return number of accounts the customer owns
     */
    public int getNumberOfAccounts() {
        return accounts.size();
    }

    /**
     * @return list of all accounts customer owns
     */
    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    /**
     * helper function to calculate interest across customers accounts
     * @return
     */
    public Money totalInterestEarned() {
        Money total = new Money("0");
        for (Account a : accounts)
            total = total.add(a.getTotalInterestEarned());
        return total;
    }
}
