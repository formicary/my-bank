package com.abc;

public enum AccountTypeEnum {
    CHECKING("Checking"),
    SAVINGS("Savings"),
    MAXI_SAVINGS("Maxi Savings");
    private final String value;

    AccountTypeEnum(final String value) {
        this.value = value;
    }
    public String getValue() {
        return value;
    }
}
