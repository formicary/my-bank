package com.abc.accenture.financial.items;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Bank {

    private final List<Customer> customers;

    public Bank() {
        customers = new ArrayList<>();
    }
}
