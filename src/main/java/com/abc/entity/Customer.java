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

    /**
     * deposit an amount into an account
     * @param amount amount to be deposited
     * @param account account to receive the deposit
     */
    void deposit(BigDecimal amount, Account account);

    /**
     * withdraw an amount from an account
     * @param amount amount to be withdrawn
     * @param account account to withdraw from
     */
    void withdraw(BigDecimal amount, Account account);

    /**
     * transfer an amount between two accounts
     * @param amount amount to be transferred
     * @param from the account to be transferred from
     * @param to the account to be transferred into
     */
    void transfer(BigDecimal amount,Account from,  Account to);

    /**
     * add an account into the customer's list of accounts.
     * @param account the account to be added.
     */
    void addAccount(Account account);
}
