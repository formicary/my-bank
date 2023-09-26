package com.abc.accenture.financial.services;

import com.abc.accenture.financial.items.Customer;
import com.abc.accenture.financial.items.account.AccountType;
import lombok.Getter;

@Getter
public final class CustomerOperation {

    private final Customer currentCustomer;
    private final AccountService accountService;

    CustomerOperation(final Customer currentCustomer, AccountService accountService) {
        this.currentCustomer = currentCustomer;
        this.accountService = accountService;
    }

    public CustomerOperation openAccount(final String accountName, final AccountType accountType) {
        if (!accountService.isAlreadyAccountName(accountName, this.currentCustomer.getAccounts())) {
            this.currentCustomer.getAccounts().put(accountName, accountService.createAccount(accountType));
        }
        this.currentCustomer.getAccounts().put(accountName, accountService.createAccount(accountType));
        return this;
    }
}