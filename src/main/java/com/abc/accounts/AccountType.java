package com.abc.accounts;

/**
 * An enum to define all the account options available
 */
public enum AccountType {

    Checking(1, "Checking"),
    Savings(2, "Savings"),
    MaxiSavings(3, "MaxiSavings");

    private int type;
    private String name;

    /**
     * Assigning the values
     * @param type 1,2,3
     * @param name Checking
     */
    AccountType(int type, String name) {
        this.type = type;
        this.name = name;
    }

    /**
     * Getter for the type
     * @return integer type
     */
    public int getType() {
        return type;
    }

    /**
     * Getter for the name
     * @return name of the account
     */
    public String getName() {
        return name;
    }
}
