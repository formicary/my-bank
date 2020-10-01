package com.abc.entity.impl;

import com.abc.entity.Account;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@code Account}
 * @author aneesh
 */
public class AccountImpl implements Account {

    private final AccountType accountType;
    private List<Transaction> transactions;

    public AccountImpl(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public AccountType getAccountType() {
        return accountType;
    }
}
