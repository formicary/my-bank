package com.abc.entity;

import com.abc.service.TransactionManager;
import com.abc.util.InterestCalculator;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }

    public void addAccount(Account account){
        this.accounts.add(account);
    }

    public List<Account> getAccounts() {
        return accounts;
    }
}
