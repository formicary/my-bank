package com.abc.entity;

import com.abc.entity.impl.AccountType;
import com.abc.entity.impl.Transaction;

import java.util.List;

/**
 * Account interface for storing transactions and account type
 * @author aneesh
 */
public interface Account {

    List<Transaction> getTransactions();
    AccountType getAccountType();
}
