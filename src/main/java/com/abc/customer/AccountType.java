package com.abc.customer;

/**
 * Represents a unique account type, used to determine interest earned for a given Account
 */
public enum AccountType {

    CHECKING("Checking Account"),
    SAVINGS("Savings Account"),
    MAXI_SAVINGS("Maxi Savings Account");

    private String name;

    AccountType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
