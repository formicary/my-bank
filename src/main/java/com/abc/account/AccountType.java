package com.abc.account;

import lombok.Getter;

public enum AccountType {

    CHECKING("Checking Account"),
    SAVINGS("Savings Account"),
    MAXI_SAVINGS("Maxi Savings Account");

    @Getter
    private final String value;

    AccountType(String value) {
        this.value = value;
    }

}
