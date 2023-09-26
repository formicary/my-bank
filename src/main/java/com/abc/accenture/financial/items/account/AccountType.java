package com.abc.accenture.financial.items.account;

import lombok.Getter;

@Getter
public enum AccountType {

    CHECKING("Checking Account"),
    SAVINGS("Savings Account"),
    MAXI_SAVINGS("Maxi Savings Account");
    private final String nameOfTittle;

    AccountType(final String nameOfTittle) {
        this.nameOfTittle = nameOfTittle;
    }
}
