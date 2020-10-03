package com.abc.entity;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * Customer interface for storing accounts and name of the customer
 * @author aneesh
 */
public interface Customer {


    /**
     * obtain name property of customer
     * @return the name of the customer
     */
    String getName();

    /**
     * get the accounts from the customer
     * @return list of all accounts
     */
    HashMap<String, Account> getAccounts();

    void deposit(BigDecimal amount, Account account);

    void withdraw(BigDecimal amount, Account account);

    void transfer(BigDecimal amount,Account from,  Account to);

    void addAccount(Account account);
}
