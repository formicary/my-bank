package com.abc;

import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private  AccountManagement accountManagement;

    public Customer(String name) {
        this.name = name;
        this.accountManagement = new StandardAccountManagement();
    }

    public Customer(String name, AccountManagement accountManagement) {
        this.name = name;
        this.accountManagement = accountManagement;
    }

    public String getName() {
        return name;
    }
    public AccountManagement getAccountManagement() {
        return accountManagement;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Customer customer = (Customer) o;

        if (name != null ? !name.equals(customer.name) : customer.name != null) return false;
        return accountManagement != null ? accountManagement.equals(customer.accountManagement) : customer.accountManagement == null;

    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }
}
