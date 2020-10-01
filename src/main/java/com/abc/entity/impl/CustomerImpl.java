package com.abc.entity.impl;

import com.abc.entity.Customer;
import com.abc.exception.InputValidator;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * Implementation of {@code Customer}
 * @author aneesh
 */
public class CustomerImpl implements Customer {
    private String name;
    private List<AccountImpl> accounts;

    public CustomerImpl(String name) {
        this.name = name;
        this.accounts = new ArrayList<AccountImpl>();
    }

    public String getName() {
        return name;
    }

    public CustomerImpl addAccount(AccountImpl account){
        InputValidator.verifyAccountOpen(account);
        this.accounts.add(account);
        return this;
    }

    public List<AccountImpl> getAccounts() {
        return accounts;
    }
}
