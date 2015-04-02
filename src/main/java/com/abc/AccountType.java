package com.abc;

public enum AccountType {

    CHECKING("Checking Account"),
    SAVINGS("Savings Account"),
    MAXI_SAVINGS("Maxi Savings Account");

    private String description;

    AccountType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
