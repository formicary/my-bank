package com.abc.entity;

import java.util.List;

public interface Customer {

    Customer addAccount(Account account);
    String getName();
    List<Account> getAccounts();

}
