package com.abc.entity;

import com.abc.entity.impl.AccountImpl;

import java.util.List;

public interface Customer {

    Customer addAccount(AccountImpl account);
    String getName();
    List<AccountImpl> getAccounts();

}
