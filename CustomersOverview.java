package com.abc;

public class CustomersOverview {

    private long id;
    private String name;
    private int numberOfAccounts;

    public CustomersOverview(long id, String name, int numberOfAccounts) {
        this.id = id;
        this.name = name;
        this.numberOfAccounts = numberOfAccounts;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfAccounts() {
        return numberOfAccounts;
    }
}
