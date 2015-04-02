package com.abc;

/**
 * Declared the types of account used by the bank
 * CHECKINGS
 * SAVINGS
 * MAXI SAVINGS
 */
public enum AccountType {

    CHECKING("Checking Account"),
    SAVINGS("Savings Account"),
    MAXI_SAVINGS("Maxi Savings Account");

    private String description;

    /**
     * Set the description of the Account type
     *
     * @param description Account type description
     */
    AccountType(String description) {
        this.description = description;
    }

    /**
     * Get the description of the Account type
     *
     * @return Account type description
     */
    public String getDescription() {
        return description;
    }
}
