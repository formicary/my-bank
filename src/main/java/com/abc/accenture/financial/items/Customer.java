package com.abc.accenture.financial.items;

import com.abc.accenture.financial.items.account.Account;
import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class Customer {

    private final String name;
    private final Map<String, Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ConcurrentHashMap<>();
    }
}
