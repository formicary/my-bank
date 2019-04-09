package com.mybank.entity;

public enum AccountType {

    CHECKING("Checking Account", 0),
    SAVINGS("Savings Account", 1),
    MAXI_SAVINGS("Maxi Savings Account", 2);

    private String name;
    private int value;

    AccountType(String name, int value)
    {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public int getValue() {
        return this.value;
    }
}
