package com.abc.entity;

import java.util.List;

/**
 * Customer interface for storing accounts and name of the customer
 * @author aneesh
 */
public interface Customer {

    Customer addAccount(Account account);
    String getName();
    List<Account> getAccounts();

}
