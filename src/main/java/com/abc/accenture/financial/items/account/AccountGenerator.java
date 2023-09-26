package com.abc.accenture.financial.items.account;

import org.springframework.stereotype.Component;

@Component
public final class AccountGenerator {

    public Account createAccount(final AccountType accountType) {
        return switch (accountType) {
            case SAVINGS -> new AccountSavings();
            case CHECKING -> new AccountChecking();
            case MAXI_SAVINGS -> new AccountMaxiSavings();
        };
    }
}
