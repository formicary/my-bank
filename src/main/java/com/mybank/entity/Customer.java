package com.mybank.entity;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    private final String name;
    private final List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }
}
