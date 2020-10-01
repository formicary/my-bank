package com.abc.entity.impl;

import com.abc.entity.Account;
import com.abc.entity.Customer;
import com.abc.exception.InputValidator;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class CustomerImpl implements Customer {
    private String name;
    private List<Account> accounts;

    public CustomerImpl(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }

    public CustomerImpl addAccount(Account account){
        InputValidator.verifyAccountOpen(account);
        this.accounts.add(account);
        return this;
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}
