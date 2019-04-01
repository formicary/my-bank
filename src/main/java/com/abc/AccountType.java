package com.abc;

/**
 * Enum class to represent different types of accounts.
 */
public enum AccountType {

    CHECKING("Checking Account"),
    SAVINGS("Savings Account"),
    MAXI_SAVINGS("Maxi Savings Account");

    private String text;

    AccountType(String text) {
        this.text = text;
    }

    /**
     * Gets the name of the account.
     * @return the name of the account
     */
    public String getName() {
        return this.text;
    }

}