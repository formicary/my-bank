package com.abc.entity;

import com.abc.entity.impl.AccountType;

import java.util.List;

public interface Account {

    List<Transaction> getTransactions();
    AccountType getAccountType();
}
