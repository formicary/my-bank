package com.abc;

/**
 * Enum class that holds types of transactions
 */
public enum TransactionType {

    WITHDRAWAL("withdrawal"),
    DEPOSIT("deposit");

    private final String type;

    TransactionType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
