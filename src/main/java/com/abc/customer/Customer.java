package com.abc.customer;

import com.abc.account.Account;

;
import java.util.ArrayList;

import static java.lang.Math.abs;

/**
 * This class stores a reference to a customers accounts
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

}
