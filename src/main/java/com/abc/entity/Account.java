package com.abc.entity;

import java.math.BigDecimal;
import java.util.List;

/**
 * Account interface for storing transactions and account type
 * @author aneesh
 */
public interface Account {

    /**
     * obtains all recorded transactions for the account
     * @return list of transactions for the account
     */
    List<Transaction> getTransactions();

    /**
     * obtains the account type assigned to the account
     * @return account type for the account
     */
    AccountType getAccountType();

    /**
     * Add a transaction to the account
     * @param transaction to be added to the account
     */
    void addTransaction(Transaction transaction);

    /**
     * return the balance of the account
     * @return sum of all transactions for the account
     */
    BigDecimal calculateBalance();

    String getAccountNumber();

    Customer getCustomer();


}
